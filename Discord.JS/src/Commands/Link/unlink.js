const { Errors } = require('../../Utils/Functions')
const emoji = require('../../../emojis.json')
const { Player } = require('../../Utils/Database/mongodb')

const { post } = require('axios');
const { EmbedBuilder } = require('discord.js');

const command = async (client, interaction, args) => {
  try {
    let player = await Player.findOne({ discord: interaction.user.id });
    if (!player) {
      let embed = new EmbedBuilder()
        .setDescription(`${emoji.error} | A sua conta já não está vinculada ao minecraft!`)
        .setColor('#FF0000')
      return interaction.reply({ ephemeral: true, embeds: [embed] });
    }

    player.code = 0;
    player.discord = "";

    player.save();

    let embed = new EmbedBuilder()
      .setDescription(`${emoji.success} | A sua conta foi desvinculada com sucesso!`)
      .setColor('#00FF00')

    interaction.reply({ ephemeral: true, embeds: [embed] }).catch(()=>{})

    try {
      await post(process.env.MINECRAFT_API + '/player/unlinked', {
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
  description: '🔗 [Link] 🔗 | Desvincule a sua conta do discord com o minecraft.'
}