const { db } = require('../Utils/Bases');
const { Errors, Timeout } = require('../Utils/Functions');
const emoji = require('../../emojis.json')

const { post } = require('axios');
const { EmbedBuilder } = require('discord.js');

const Auto = async (client) => {
  try {
    let guild = client.guilds.cache.get(process.env.GUILD_ID)
    if (!guild) return console.log('O "GUILD_ID" configurado no ".env" não foi encontrado em minha lista de servidores...');

    let channel = guild.channels.cache.get(process.env.EVENTS_CHANNEL_ID)
    if (!channel) return console.log('O "EVENTS_CHANNEL_ID" configurado no ".env" não foi encontrado em minha lista de canais do "GUILD_ID"...');

    let code = Math.floor(Math.random() * (999999999 - 100000000 + 1)) + 100000000
    if (db.events.chests.find((r) => r.code == code)) return Auto(client);

    db.events.chests.push({ user: 'Automatic', code })
    setTimeout(() => {
      if (db.events.chests.find((r)=> r.code == code)) {
        db.events.chests.splice(db.events.chests.findIndex((r) => r.user == 'Automatic' && r.code == code), 1)
      }
    }, 5 * 60 * 1000)

    let embed = new EmbedBuilder()
      .setTitle(`${emoji.chest} | Um Baú Apareceu!`)
      .setDescription(`Digite a senha do baú para abrir:\n**/bau** \`${code}\``)
      .setThumbnail(guild.iconURL())
      .setImage('https://media.discordapp.net/attachments/733482744105140354/1241604016186851449/red_chest.gif?ex=664acd59&is=66497bd9&hm=5b90b5039941a5a9b246fc6f384467233221e03319878bd773d06254a76fa2bf&=')
      .setColor('#976c25')
      .setTimestamp()
    channel.send({ embeds: [embed] });

    try {
      await post(process.env.MINECRAFT_API + '/events/spawned', {
        headers: { 'Authorization': process.env.MINECRAFT_API_TOKEN }
      })
    } catch(e) {}

    await Timeout((Math.floor(Math.random() * (50 - 30 + 1)) + 30) * 60 * 1000);
    return Auto(client);
  } catch(err) {
    return Errors(err, `Automatic ${__filename}`)
      .then(() => Auto(client, r))
      .catch((e) => e)
  }
}

module.exports = Auto;