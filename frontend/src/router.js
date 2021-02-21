import Vue from 'vue'
import Router from 'vue-router'
import Signin from "@/components/Signin";
import Signin2 from "@/components/Signin2";
import FlowGuard from './flow-guard'

Vue.use(Router);

const router = new Router({
  mode: 'history',
  routes: [
    {
      path: '/',
      redirect: '/sign-in'
    },
    {
      path: '/index.html',
      redirect: '/sign-in'
    },
    {
      path: '/sign-in',
      name: 'Signin',
      component: Signin,
      beforeEnter: FlowGuard
    },
    {
      path: '/sign-in/otp',
      name: 'Signin2',
      component: Signin2,
      beforeEnter: FlowGuard
    }
  ]
});

export default router;