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
    children: [
      {
        path: 'dashboard',
        name: 'dashboard',
        component: () => import('@/views/dashboard/index'),
        meta: { title: '首页', icon: 'dashboard', role: ['user','admin'] }
      }
    ]
  }
]

/**
 * 异步动态路由，根据权限来
 */
export const asyncRouterMap = [

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
        hidden: true
      }
    ]
  },

  {
    path: '/userStageTest',
    component: Layout,
    children: [
      {
        path: 'userStageTest',
        name: 'userStageTest',
        component: () => import('@/views/user/stageLearnAndTest/UserStageTest'),
        meta: { title: '阶段考试', icon: 'table', role: ['user'] }
      },
      {
        path: 'userStageTestTest',
        name: 'userStageTestTest',
        component: () => import('@/views/user/stageLearnAndTest/UserStageTestTest'),
        meta: { title: '阶段考试题目', icon: 'table', role: ['user'] },
        hidden: true
      },
      {
        path: 'userStageTestTestDetail',
        name: 'userStageTestTestDetail',
        component: () => import('@/views/user/stageLearnAndTest/UserStageTestTestDetail'),
        meta: { title: '阶段考试题目详情', icon: 'table', role: ['user'] },
        hidden: true
      }

    ]
  },

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

  {
    path: '/404',
    component: () => import('@/views/errorPage/404'),
    hidden: true
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
