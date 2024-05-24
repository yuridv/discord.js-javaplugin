const { Errors, Files, Timeout } = require('../Utils/Functions');
const { ActivityType } = require("discord.js")

const event = async (client) => {
  try {
    console.log('[Discord.JS BOT]=> Started Successfully!')
    client.user.setPresence({
      status: 'dnd',
      activities: [
        { name: `Em Construção...`, type: ActivityType.Listening }, 
        { name: `Created By MrDragon`, type: ActivityType.Playing },
        { name: `Em Desenvolvimento...`, type: ActivityType.Streaming }, 
        { name: `Desenvolvido pelo MrDragon`, type: ActivityType.Watching },
      ],
    });

    let Automatic = Files('./src/Automatic/', '../../Automatic', 0, 1);
    for (let a in Automatic) {
      await Timeout((Math.floor(Math.random() * (30 - 5 + 1)) + 5) * 60 * 1000);
      Automatic[a](client);
    }
  } catch(err) {
    return Errors(err, `Event ${__filename}`)
      .then(() => event(client))
      .catch((e) => e)
  }
}

module.exports = event;