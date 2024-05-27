const { Errors } = require('../../Utils/Functions')
const emoji = require('../../../emojis.json')
const { Player } = require('../../Utils/Database/mongodb')

const { EmbedBuilder } = require('discord.js');

const { get } = require('axios');

const command = async (client, interaction, args) => {
  try {
    let user = args.find(r=> r.name == 'jogador')?.value?.replace('@','').replace('<','').replace('>','');
    if (!user) user = interaction.user.id

    user = interaction.guild.members.cache.get(user)
    if (!user) {
      let embed = new EmbedBuilder()
        .setDescription(`${emoji.error} | Não consegui encontrar o jogador que você marcou!`)
        .setColor('#FF0000')
      return interaction.reply({ ephemeral: true, embeds: [embed] });
    }

    let player = await Player.findOne({ discord: user.id });
    if (!player) {
      let embed = new EmbedBuilder()
        .setDescription(`${emoji.error} | O jogador marcado não possui a sua conta vinculada com o minecraft!`)
        .setColor('#FF0000')
      return interaction.reply({ ephemeral: true, embeds: [embed] });
    }

    let embed_wait = new EmbedBuilder()
      .setDescription(`${emoji.loading} Carregando o perfil do jogador...`)
      .setColor('#FFFF00')
    await interaction.reply({ ephemeral: true, embeds: [embed_wait] }).catch(()=>{})

    let status = { emoji: emoji.error, text: 'O player não está jogando no servidor!' }
    try {
      let res = await get(process.env.MINECRAFT_API + '/player/status?player=' + player._id, {
        headers: { 'Authorization': process.env.MINECRAFT_API_TOKEN }
      })
      if (res && res.data && res.data.status == 'Online') status = { emoji: emoji.success, text: 'O player está jogando no servidor!' }
    } catch(e) {}

    let embed = new EmbedBuilder()
      .setTitle(`${emoji.project_chest} | Perfil do jogador ${user.user.globalName}!`)
      .setDescription(`*Aqui você pode ver as informações gerais de um jogador!*`)
      .addFields(
        { name: `**> :video_game: Conta do Minecraft:**`, value: `\`\`\`• ${player._id}\`\`\`` },
        { name: `**> :star2: Level:**`, value: `\`\`\`• ${player.level}\`\`\``, inline: true },
        { name: `**> ✨ Experiência:**`, value: `\`\`\`• ${player.xp}/${(((player.level * 1.8) * 500) + 1000).toFixed(0)}\`\`\``, inline: true },
        { name: `**> ${status.emoji} Player Status:**`, value: `\`\`\`• ${status.text}\`\`\`` },
        { name: `**> 💸 Discord Money:**`, value: `\`\`\`• ${player.money}\`\`\``, inline: true },
        { name: `**> 💰 Total Money:**`, value: `\`\`\`• ${player.money_total}\`\`\``, inline: true },
        { name: `**> ${emoji.chest} Baús Abertos:**`, value: `\`\`\`• ${player.chests}\`\`\`` },
      )
      .setThumbnail(`https://mc-heads.net/head/${player._id}.png`)
      .setColor('#FFFF00')
      .setTimestamp()
    return interaction.editReply({ ephemeral: true, embeds: [embed] })
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
      "name": "jogador",
      "description": "Marque ou coloque o ID de um jogador",
      "type": 3,
      "required": false
    },
  ]
}