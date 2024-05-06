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
    console.log(err)
    console.log(`[ERROR Events/ready]=> ${err}`)
  }
}

module.exports = event;