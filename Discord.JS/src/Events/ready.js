const { Errors } = require('../Utils/Functions');
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
  } catch(err) {
    return Errors(err, `Routes ${__filename}`)
      .then(() => { return route(req, res) })
      .catch((e) => { return res.status(e.status || 500).send(e) })
  }
}

module.exports = event;