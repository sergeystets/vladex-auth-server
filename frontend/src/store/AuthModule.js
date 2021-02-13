const AuthModule = {
  state: {
    phone: null,
    otpExpirationSeconds: null
  },
  mutations: {
    setPhone(state, payload) {
      state.phone = payload
    },
    setOtpExpirationSeconds(state, payload) {
      state.otpExpirationSeconds = payload
    }
  }
  ,
  actions: {
    phoneVerified({commit}, payload) {
      commit('setPhone', payload.phone);
      commit('setOtpExpirationSeconds', payload.otpExpirationSeconds)
    },
  },
  getters: {
    phone(state) {
      return state.phone;
    },
    otpExpirationSeconds(state) {
      return state.otpExpirationSeconds;
    }
  }
}

export default AuthModule
