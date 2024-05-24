const { Errors } = require('../../Utils/Functions')
const { Player } = require('../../Utils/Database/mongodb')

const event = async (client, interaction) => {
  try {
    if (interaction.isChatInputCommand()) {
      if (!client.commands.find(r=> r.name == interaction.commandName)) return;
      await client.commands.find(r=> r.name == interaction.commandName).route(client, interaction, interaction.options._hoistedOptions)
    }
  } catch(err) {
    return Errors(err, `Event ${__filename}`)
      .then(() => event(client, interaction))
      .catch((e) => e)
  }
}

module.exports = event;