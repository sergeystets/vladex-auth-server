import axios from 'axios'

const AXIOS = axios.create({
  baseURL: process.env.VUE_APP_SERVER_URL,
  timeout: 10000
})

export default {

  signIn(phoneNumber) {
    return AXIOS.post("/sign-in", {phoneNumber: phoneNumber});
  }
}