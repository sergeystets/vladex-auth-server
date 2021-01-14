// vue.config.js
module.exports = {
  outputDir: 'target/dist',
  assetsDir: 'static',

  pluginOptions: {
    i18n: {
      locale: 'en',
      fallbackLocale: 'en',
      localeDir: 'locales',
      enableInSFC: true
    }
  }
};
