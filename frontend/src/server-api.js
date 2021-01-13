import axios from 'axios'

const AXIOS = axios.create({
  baseURL: process.env.VUE_APP_SERVER_URL,
  timeout: 10000
})

export default {

  singIn(phoneNumber) {
    return AXIOS.post("/sing-in", {phoneNumber: phoneNumber});
  }
}