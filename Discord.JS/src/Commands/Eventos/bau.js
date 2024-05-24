const { Errors, Experience } = require('../../Utils/Functions')
const { db } = require('../../Utils/Bases')
const emoji = require('../../../emojis.json')
const { Player } = require('../../Utils/Database/mongodb')

const { EmbedBuilder } = require('discord.js');

let opend = [];

const command = async (client, interaction, args) => {
  try {
    let player = await Player.findOne({ discord: interaction.user.id });
    if (!player) {
      let embed = new EmbedBuilder()
        .setDescription(`${emoji.error} | A sua conta do discord não está vinculada a nenhuma conta do minecraft!`)
        .setColor('#FF0000')
      return interaction.reply({ ephemeral: true, embeds: [embed] });
    }

    let senha = args.find(r=> r.name == 'senha').value;
    if (!senha) {
      let embed = new EmbedBuilder()
        .setDescription(`${emoji.error} | Coloque a senha que apareceu na mensagem do baú spawnado!`)
        .setColor('#FF0000')
      return interaction.reply({ ephemeral: true, embeds: [embed] });
    }

    let bau = db.events.chests.find((r)=> r.code == senha && (r.user == interaction.user.id || r.user == 'Automatic'));
    if (!bau) {
      let embed = new EmbedBuilder()
        .setDescription(`${emoji.error} | A senha está errada ou o baú não está mais valido!`)
        .setColor('#FF0000')
      return interaction.reply({ ephemeral: true, embeds: [embed] });
    }

    if (opend.find((r) => r == interaction.user.id)) {
      let embed = new EmbedBuilder()
        .setDescription(`${emoji.error} | Você precisa esperar 1 hora para poder abrir outro baú!`)
        .setColor('#FF0000')
      return interaction.reply({ ephemeral: true, embeds: [embed] });
    }
    opend.push(interaction.user.id);
    setTimeout(()=> {
      if (opend.find((r) => r == interaction.user.id)) {
        opend.splice(opend.findIndex((r) => r == interaction.user.id), 1)
      }
    }, 60 * 60 * 1000)

    db.events.chests.splice(db.events.chests.findIndex((r) => r.user == bau.user && r.code == bau.code), 1);

    let types = [
      { min: 95, name: "Lendário", bonus: { min: 3000, max: 3500, item: 2 } },
      { min: 80, name: "Épico", bonus: { min: 2000, max: 3000, item: 1 } },
      { min: 60, name: "Raro", bonus: { min: 1500, max: 2000 } },
      { min: 40, name: "Incomum", bonus: { min: 1000, max: 1500 } },
      { min: 0, name: "Comum", bonus: { min: 500, max: 1500 } },
    ]

    let random = Math.floor(Math.random() * (100 - 1 + 1)) + 1;
    let type = types.find((r)=> random >= r.min);

    let xp = Math.floor(Math.random() * (type.bonus.max - type.bonus.min + 1)) + type.bonus.min;
    let money = Math.floor(Math.random() * (type.bonus.max - type.bonus.min + 1)) + type.bonus.min;

    player.money += money;
    player.chests += 1;
    await Experience(player, xp, interaction);

    let embed = new EmbedBuilder()
      .setTitle(`${bau.user == 'Automatic' ? emoji.chest : emoji.ender_chest} | Um Baú foi Aberto!`)
      .setDescription(`${interaction.user}, você abriu um baú!
• Senha do Baú: \`${bau.code}\`
• Raridade do Baú: **_${type.name}_**`)
      .addFields(
        { name: `**> ${emoji.project_chest} Foi encontrado:**`, value: `\`\`\`• R$ ${money},00\`\`\`` },
        { name: `**> ✨ Experiência ganha:**`, value: `\`\`\`• ${xp}\`\`\`` },
        { name: `**> ${emoji.chest} Baús abertos:**`, value: `\`\`\`• ${player.chests}\`\`\`` },
      )
      .setThumbnail(interaction.guild.iconURL())
      .setImage(bau.user == 'Automatic' ? 'https://media.discordapp.net/attachments/733482744105140354/1241604016186851449/red_chest.gif?ex=664acd59&is=66497bd9&hm=5b90b5039941a5a9b246fc6f384467233221e03319878bd773d06254a76fa2bf&=' : 'https://media.discordapp.net/attachments/733482744105140354/1241604016719270029/royal_chest.gif?ex=664acd59&is=66497bd9&hm=35ba87193871c3ca7dcfcc675a4a7cb27c7e18519d347f502156dadff2efb3cf&=')
      .setColor('#00FF00')
      .setTimestamp()
    return interaction.reply({ content: `Parabéns ${interaction.user}, você acaba de abrir um baú!`, embeds: [embed] }).catch(()=>{})
  } catch(err) {
    return Errors(err, `Command ${__filename}`)
      .then(() => command(client, interaction, args))
      .catch((e) => e)
  }
}

module.exports = { 
  route: command,
  description: '🎉 [Evento] 🎉 | Abra algum baú que foi spawnado utilizando a senha.',
  options: [
    {
      "name": "senha",
      "description": "Coloque a senha que apareceu na mensagem do baú spawnado.",
      "type": 3,
      "required": true
    },
  ]
}