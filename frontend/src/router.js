import Vue from 'vue'
import Router from 'vue-router'
import Signin from "@/components/User/Signin";
import Signin2 from "@/components/User/Signin2";
import FlowGuard from './flow-guard'

Vue.use(Router);

const router = new Router({
  mode: 'history',
  routes: [
    {
      path: '/',
      redirect: '/login'
    },
    {
      path: '/index.html',
      redirect: '/login'
    },
    {
      path: '/login',
      name: 'Signin',
      component: Signin,
      beforeEnter: FlowGuard
    },
    {
      path: '/login/otp',
      name: 'Signin2',
      component: Signin2,
      beforeEnter: FlowGuard
    }
  ]
});

export default router;