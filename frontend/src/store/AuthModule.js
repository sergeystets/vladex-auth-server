import api from '../server-api'

const AuthModule = {
  state: {
    phone: null,
    verifyPhoneLoading: false,
    loadingOtp: false,
    otpExpirationSeconds: null
  },
  mutations: {
    setVerifyPhoneLoading(state, payload) {
      state.verifyPhoneLoading = payload
    },
    setPhone(state, payload) {
      state.phone = payload
    },
    setLoadingOtp(state, payload) {
      state.loadingOtp = payload
    },
    setOtpExpirationSeconds(state, payload) {
      state.otpExpirationSeconds = payload
    }
  }
  ,
  actions: {
    singIn({commit}, payload) {
      commit('setVerifyPhoneLoading', true)
      api.singIn(payload.phone).then(function (response) {
        commit('setVerifyPhoneLoading', false)
        commit('setPhone', payload.phone);
        commit('setOtpExpirationSeconds', response.data.otpExpirationSeconds)
      });
    },
  },
  getters: {
    verifyPhoneLoading(state) {
      return state.verifyPhoneLoading;
    },
    loadingOtp(state) {
      return state.loadingOtp;
    },
    phone(state) {
      return state.phone;
    },
    otpExpirationSeconds(state) {
      return state.otpExpirationSeconds;
    }
  }
}

export default AuthModule
