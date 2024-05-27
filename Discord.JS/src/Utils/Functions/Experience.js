let Errors = require('./Errors');
const { EmbedBuilder } = require('discord.js');

const route = (player, xp, message) => new Promise(async (res,rej) => {
  try {
    player.xp = (player.xp + xp).toFixed(0);

    let next = ((player.level * 1.8) * 500) + 1000
    if (player.xp >= next) {
      player.level += 1;
      player.xp = (player.xp - next).toFixed(0);

      next = ((player.level * 1.8) * 500) + 1000
      if (player.xp >= next) {
        return route(player, 0, message)
          .then((r)=> res(r))
          .catch((e)=> rej(e))
      }

      if (message) {
        let embed = new EmbedBuilder()
          .setDescription(`Parabéns ${(message.author || message.user)}, você acabou de upar para o level **__${player.level}__**!`)
          .setColor('#FFFF00')
          .setTimestamp();
        message.channel.send({ content: `${(message.author || message.user)}`, embeds: [embed] })
          .then((r) => {
            setTimeout(() => { r.delete().catch(()=>{}) }, 10 * 1000)
          })
          .catch(()=>{})
      }
    }

    player.save();
    return res();
  } catch(err) {
    return Errors(err, `Functions ${__filename}`)
      .then(() => {
        return route(player, xp, message)
          .then((r) => res(r))
          .catch((e) => rej(e))
      })
      .catch((e) => rej(err));
  }
})

module.exports = route