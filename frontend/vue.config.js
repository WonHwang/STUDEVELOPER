module.exports = {
  configureWebpack: {},
  devServer: {
    host: '0.0.0.0',
    https: false,
    port: 8083,
    open: true,
    proxy: {
      '/api/v1': {
        // target: 'https://i5a503.p.ssafy.io:8080',
        target: 'http://localhost:8080/',
        logLevel: 'debug',
      },
    },
    historyApiFallback: true,
    hot: true,
    disableHostCheck: true,
  },
  transpileDependencies: ['vuetify'],
  outputDir: '../backend/src/main/resources/dist',
};
