import Vue from 'vue';
import Vuetify from 'vuetify/lib';

Vue.use(Vuetify);

Vue.filter('formatCounter', function (value) {
  if (value) {
    var sec_num = parseInt(value, 10)
    var hours = Math.floor(sec_num / 3600) % 24
    var minutes = Math.floor(sec_num / 60) % 60
    var seconds = sec_num % 60
    return [hours, minutes, seconds]
    .map(v => v < 10 ? "0" + v : v)
    .filter((v, i) => v !== "00" || i > 0)
    .join(":")
  }
});

const vuetify = new Vuetify({
  icons: {
    iconfont: 'mdi'
  }
});

export default vuetify
