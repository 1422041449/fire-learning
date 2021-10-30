import { asyncRouterMap, constantRouterMap } from '@/router'
import store from '@/store'

function hasPermission(roles, route) {
  if (route.meta && route.meta.role) {
    let some = roles.some(role => route.meta.role.indexOf(role) >= 0)
    return some
  } else {
    return true
  }
}

const permission = {
  state: {
    routers: constantRouterMap,
    addRouters: []
  },
  mutations: {
    SET_ROUTERS: (state, routers) => {
      state.addRouters = routers
      state.routers = constantRouterMap.concat(routers)
    }
  },
  actions: {
    generateRoutes({ commit }, data) {
      return new Promise(resolve => {
        const roles = data
        const accessedRouters = asyncRouterMap.filter(v => {
          // if (roles.indexOf('admin') >= 0) return true;
          let b = hasPermission(roles, v)
          if (hasPermission(roles, v)) {
            if (v.children && v.children.length > 0) {
              v.children = v.children.filter(child => {
                if (hasPermission(roles, child)) {
                  return child
                }
                return false
              })
              return v
            } else {
              return v
            }
          }
          return false
        })
        commit('SET_ROUTERS', accessedRouters)
        resolve()
      })
    }
  }
}

export default permission
