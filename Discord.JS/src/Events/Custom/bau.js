const { db } = require('../../Utils/Bases');
const { Errors } = require('../../Utils/Functions');
const { Player } = require('../../Utils/Database/mongodb')
const emoji = require('../../../emojis.json')

const { EmbedBuilder } = require('discord.js');

const event = async (client, message) => {
  try {
    let guild = client.guilds.cache.get(process.env.GUILD_ID)
    if (!guild) return console.log('O "GUILD_ID" configurado no ".env" não foi encontrado em minha lista de servidores...');

    let channel = guild.channels.cache.get(process.env.EVENTS_CHANNEL_ID)
    if (!channel) return console.log('O "EVENTS_CHANNEL_ID" configurado no ".env" não foi encontrado em minha lista de canais do "GUILD_ID"...');

    let player = await Player.findOne({ discord: message.author.id });
    if (!player) return;

    let code = Math.floor(Math.random() * (999999999 - 100000000 + 1)) + 100000000
    if (db.events.chests.find((r)=> r.code == code)) return Auto(client);

    let embed = new EmbedBuilder()
      .setTitle(`${emoji.ender_chest} | Um Baú Exclusivo Apareceu!`)
      .setDescription(`Digite a senha do baú para abrir:\n**/bau** \`${code}\``)
      .setThumbnail(guild.iconURL())
      .setImage('https://media.discordapp.net/attachments/733482744105140354/1241604016719270029/royal_chest.gif?ex=664acd59&is=66497bd9&hm=35ba87193871c3ca7dcfcc675a4a7cb27c7e18519d347f502156dadff2efb3cf&=')
      .setColor('#091315')
      .setTimestamp()
    message.reply({ content: `Parabéns ${message.author}, você acaba de spawnar um baú exclusivo!`, embeds: [embed] });

    db.events.chests.push({ user: message.author.id, code })
    setTimeout(() => {
      if (db.events.chests.find((r)=> r.code == code)) {
        db.events.chests.splice(db.events.chests.findIndex((r)=> r.user == message.author.id && r.code == code), 1)
      }
    }, 10 * 60 * 1000)
  } catch(err) {
    return Errors(err, `Event ${__filename}`)
      .then(() => event(client))
      .catch((e) => e)
  }
}

module.exports = event;