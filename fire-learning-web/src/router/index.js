import Vue from 'vue'
import Router from 'vue-router'
/* Layout */
import Layout from '@/layout'

Vue.use(Router)

/**
 * Note: sub-menu only appear when route children.length >= 1
 * Detail see: https://panjiachen.github.io/vue-element-admin-site/guide/essentials/router-and-nav.html
 *
 * hidden: true                   if set true, item will not show in the sidebar(default is false)
 * alwaysShow: true               if set true, will always show the root menu
 *                                if not set alwaysShow, when item has more than one children route,
 *                                it will becomes nested mode, otherwise not show the root menu
 * redirect: noRedirect           if set noRedirect will no redirect in the breadcrumb
 * name:'router-name'             the name is used by <keep-alive> (must set!!!)
 * meta : {
    roles: ['admin','editor']    control the page roles (you can set multiple roles)
    title: 'title'               the name show in sidebar and breadcrumb (recommend set)
    icon: 'svg-name'/'el-icon-x' the icon show in the sidebar
    breadcrumb: false            if set false, the item will hidden in breadcrumb(default is true)
    activeMenu: '/example/list'  if set path, the sidebar will highlight the path you set
  }
 */

/**
 * constantRoutes
 * a base page that does not have permission requirements
 * all roles can be accessed
 */

/**
 * 公共路由
 */
export const constantRouterMap = [
  {
    path: '/login',
    component: () => import('@/views/login/index'),
    hidden: true
  },

  {
    path: '/',
    component: Layout,
    redirect: '/dashboard',
    children: [{
      path: 'dashboard',
      name: 'dashboard',
      component: () => import('@/views/dashboard/index'),
      meta: { title: '首页', icon: 'dashboard' }
    }]
  }
  //
  // {
  //   path: '/404',
  //   component: () => import('@/views/404'),
  //   hidden: true
  // },
  //
  // {
  //   path: '/example',
  //   component: Layout,
  //   redirect: '/example/table',
  //   name: 'Example',
  //   meta: { title: 'Example', icon: 'el-icon-s-help' },
  //   children: [
  //     {
  //       path: 'table',
  //       name: 'Table',
  //       component: () => import('@/views/table/index'),
  //       meta: { title: 'Table', icon: 'table' }
  //     },
  //     {
  //       path: 'tree',
  //       name: 'Tree',
  //       component: () => import('@/views/tree/index'),
  //       meta: { title: 'Tree', icon: 'tree' }
  //     }
  //   ]
  // },
  //
  // {
  //   path: '/nested',
  //   component: Layout,
  //   redirect: '/nested/menu1',
  //   name: 'Nested',
  //   meta: {
  //     title: '多菜单',
  //     icon: 'nested'
  //   },
  //   children: [
  //     {
  //       path: 'menu1',
  //       component: () => import('@/views/nested/menu1/index'), // Parent router-view
  //       name: 'Menu1',
  //       meta: { title: 'Menu1' },
  //       children: [
  //         {
  //           path: 'menu1-1',
  //           component: () => import('@/views/nested/menu1/menu1-1'),
  //           name: 'Menu1-1',
  //           meta: { title: 'Menu1-1' }
  //         },
  //         {
  //           path: 'menu1-2',
  //           component: () => import('@/views/nested/menu1/menu1-2'),
  //           name: 'Menu1-2',
  //           meta: { title: 'Menu1-2' },
  //           children: [
  //             {
  //               path: 'menu1-2-1',
  //               component: () => import('@/views/nested/menu1/menu1-2/menu1-2-1'),
  //               name: 'Menu1-2-1',
  //               meta: { title: 'Menu1-2-1' }
  //             },
  //             {
  //               path: 'menu1-2-2',
  //               component: () => import('@/views/nested/menu1/menu1-2/menu1-2-2'),
  //               name: 'Menu1-2-2',
  //               meta: { title: 'Menu1-2-2' }
  //             }
  //           ]
  //         },
  //         {
  //           path: 'menu1-3',
  //           component: () => import('@/views/nested/menu1/menu1-3'),
  //           name: 'Menu1-3',
  //           meta: { title: 'Menu1-3' }
  //         }
  //       ]
  //     },
  //     {
  //       path: 'menu2',
  //       component: () => import('@/views/nested/menu2/index'),
  //       name: 'Menu2',
  //       meta: { title: 'menu2' }
  //     }
  //   ]
  // },
  //
  // {
  //   path: 'external-link',
  //   component: Layout,
  //   children: [
  //     {
  //       path: 'https://panjiachen.github.io/vue-element-admin-site/#/',
  //       meta: { title: 'External Link', icon: 'link' }
  //     }
  //   ]
  // }
]

/**
 * 异步动态路由，根据权限来
 */
export const asyncRouterMap = [

  /**
   * 管理员
   */
  {
    path: '/userMgr',
    component: Layout,
    children: [
      {
        path: 'userMgr',
        name: 'UserMgr',
        component: () => import('@/views/admin/userMgr/index'),
        meta: { title: '用户管理', icon: 'form', role: ['admin'] }
      }
    ]
  },

  {
    path: '/exercises',
    component: Layout,
    children: [
      {
        path: 'exercises',
        name: '题库管理',
        component: () => import('@/views/admin/exercises/ExerciseBank'),
        meta: { title: '题库管理', icon: 'form', role: ['admin'] }
      }
    ]
  },

  {
    path: '/stageMgr',
    component: Layout,
    children: [
      {
        path: 'stageMgr',
        name: 'stageMgr',
        component: () => import('@/views/admin/stage/StageInfo'),
        meta: { title: '阶段学习管理', icon: 'form', role: ['admin'] }
      },
      {
        path: 'stageLearn',
        name: 'stageLearn',
        component: () => import('@/views/admin/stage/StageLearn'),
        meta: { title: '阶段学习题', icon: 'form', role: ['admin'] },
        hidden: true
      },
      {
        path: 'stageTest',
        name: 'stageTest',
        component: () => import('@/views/admin/stage/StageTest'),
        meta: { title: '阶段考试题', icon: 'form', role: ['admin'] },
        hidden: true
      }
    ]
  },

  /**
   * 用户
   */
  {
    path: '/userStageLearn',
    component: Layout,
    children: [
      {
        path: 'userStageLearn',
        name: 'userStageLearn',
        component: () => import('@/views/user/stageLearnAndTest/UserStageLearn'),
        meta: { title: '阶段学习', icon: 'table', role: ['user'] }
      },
      {
        path: 'userStageLearnTest',
        name: 'userStageLearnTest',
        component: () => import('@/views/user/stageLearnAndTest/UserStageLearnTest'),
        meta: { title: '阶段学习题目', icon: 'table', role: ['user'] },
        hidden:true
      }
    ]
  },

  // 404 page must be placed at the end !!!
  { path: '*', redirect: '/404', hidden: true }
]

// export default new Router({
//   routes: constantRouterMap
// })

const createRouter = () => new Router({
  // mode: 'history', // require service support
  scrollBehavior: () => ({ y: 0 }),
  routes: constantRouterMap
})

export function resetRouter() {
  const newRouter = createRouter()
  router.matcher = newRouter.matcher // reset router
}

const router = createRouter()
export default router
