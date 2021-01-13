import Vue from 'vue'
import Vuex from 'vuex'

import AuthModule from './AuthModule'

Vue.use(Vuex)

export const store = new Vuex.Store({
  modules: {
    auth: AuthModule,
  }
})
