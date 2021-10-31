const getters = {
  sidebar: state => state.app.sidebar,
  device: state => state.app.device,
  token: state => state.userinfo.token,
  avatar: state => state.userinfo.avatar,
  name: state => state.userinfo.name,
  role: state => state.userinfo.role,
  username:state =>state.userinfo.username,
  addRouters: state => state.permission.addRouters,
  routers: state => state.permission.routers
}
export default getters
