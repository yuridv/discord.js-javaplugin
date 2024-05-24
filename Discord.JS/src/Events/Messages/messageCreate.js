const { Errors, Experience } = require('../../Utils/Functions')
const { Player } = require('../../Utils/Database/mongodb')

let block = [];

const event = async (client, message) => {
  try {
    if(message.author.bot) return;
    if(message.channel.type == "dm") return;

    if (!block.find((r) => r == message.author.id)) {
      block.push(message.author.id)
      setTimeout(() => {
        block.splice(block.findIndex((r) => r == message.author.id), 1);
      }, 10 * 1000)

      let player = await Player.findOne({ discord: message.author.id });
      if (!player) return;

      if ((Math.floor(Math.random() * (100 - 1 + 1)) + 1) > 95) client.emit('bau', message);

      let xp = (Math.floor(Math.random() * (100 - 5 + 1)) + 5);
      await Experience(player, xp, message);
    }

  } catch(err) {
    return Errors(err, `Event ${__filename}`)
      .then(() => event(client, message))
      .catch((e) => e)
  }
}

module.exports = event;