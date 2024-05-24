const { Errors } = require('../../Utils/Functions')
const { Player } = require('../../Utils/Database/mongodb')
const emoji = require('../../../emojis.json')

const { post } = require('axios');
const { EmbedBuilder } = require('discord.js');

const command = async (client, interaction, args) => {
  try {
    let user = await Player.findOne({ discord: interaction.user.id });
    if (user) {
      let embed = new EmbedBuilder()
        .setDescription(`${emoji.error} | A sua conta do discord já está vinculado a uma conta do minecraft!`)
        .setColor('#FF0000')
      return interaction.reply({ ephemeral: true, embeds: [embed] });
    }

    let code = args.find(r=> r.name == 'codigo').value;
    if (!code) {
      let embed = new EmbedBuilder()
        .setDescription(`${emoji.error} | Coloque o codigo que apareceu ao digitar o comando /dslink dentro do minecraft!`)
        .setColor('#FF0000')
      return interaction.reply({ ephemeral: true, embeds: [embed] });
    }

    if (!Number(code)) {
      let embed = new EmbedBuilder()
        .setDescription(`${emoji.error} | O codigo precisa ser apenas números! Utilize o comando /dslink dentro do minecraft para obter o seu codigo!`)
        .setColor('#FF0000')
      return interaction.reply({ ephemeral: true, embeds: [embed] });
    }

    let player = await Player.findOne({ code: code });
    if (!player) {
      let embed = new EmbedBuilder()
        .setDescription(`${emoji.error} | O codigo digitado não é valido! Utilize o comando /dslink dentro do minecraft para obter o seu codigo!`)
        .setColor('#FF0000')
      return interaction.reply({ ephemeral: true, embeds: [embed] });
    }

    player.code = 0;
    player.discord = interaction.user.id;

    player.save();

    let embed = new EmbedBuilder()
      .setDescription(`${emoji.success} | A sua conta foi vinculada com sucesso!`)
      .setColor('#00FF00')

    interaction.reply({ ephemeral: true, embeds: [embed] }).catch(()=>{})

      try {
        await post(process.env.MINECRAFT_API + '/player/linked', {
          player: player._id
        }, {
          headers: { 'Authorization': process.env.MINECRAFT_API_TOKEN }
        })
      } catch(e) {}
  } catch(err) {
    return Errors(err, `Command ${__filename}`)
      .then(() => command(client, interaction, args))
      .catch((e) => e)
  }
}

module.exports = { 
  route: command,
  description: '🔗 [Link] 🔗 | Vincule a sua conta do minecraft com o discord.',
  options: [
    {
      "name": "codigo",
      "description": "Coloque o codigo que apareceu ao digitar o comando /dslink dentro do minecraft.",
      "type": 3,
      "required": true
    },
  ]
}