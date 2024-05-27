const { db } = require('../../Utils/Bases')
const { Errors, Validate } = require('../../Utils/Functions')

const route = async (req, res) => {
  try {
    req.query = await Validate(req.query, {
      id: { required: true, type: 'string' }
    })

    if (!db.client) return { status: 500, error: `Não foi possivel fazer a conexão com o discord...` } 

    let user = db.client.users.cache.get(req.query.id)
    if (!user) return { status: 500, error: `Não foi possivel encontrar esse usuário...` } 

    if (!user.globalName || !user.username) return { status: 500, error: `Não foi possivel encontrar o nome desse usuário...` } 

    return { status: 200, name: user.globalName || user.username }
  } catch(err) {
    return Errors(err, `ROUTE ${__filename}`)
      .then(() => { return route(req, res) })
      .catch((e) => e)
  }
}

module.exports = { 
  route, 
  method: 'GET'
}