const { Errors } = require('../../Utils/Functions')
const { Player } = require('../../Utils/Database/mongodb')

const event = async (client, interaction) => {
  try {
    if (interaction.isChatInputCommand()) {

      let table = await Player.findById("Teste_Plugin")
      if (!table) table = new Player({ _id: "Teste_Discord" })
      table.nickname = 'discord'
      table.save();

      if (!client.commands.find(r=> r.name == interaction.commandName)) return;
      await client.commands.find(r=> r.name == interaction.commandName).route(client, interaction, interaction.options._hoistedOptions)
    }
  } catch(err) {
    return Errors(err, `Routes ${__filename}`)
      .then(() => { return route(req, res) })
      .catch((e) => { return res.status(e.status || 500).send(e) })
  }
}

module.exports = event;