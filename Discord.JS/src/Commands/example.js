const { EmbedBuilder } = require('discord.js');

const command = async (client, interaction, args) => {
  try {

    interaction.reply({ content: 'Command example...' }).catch(()=>{})
  } catch(err) {
    console.log(err)
    console.log(`[ERROR Commands/help]=> ${err}`)
  }
}

module.exports = { 
  route: command,
  description: '📚 [Example] 📚 | Comando de exemplo.'
}