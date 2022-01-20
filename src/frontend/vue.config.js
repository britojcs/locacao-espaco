// var baseURL = window.location.protocol + '//' + window.location.host;
// if (window.location.hostname === 'localhost')
//   baseURL = 'http://localhost:8000';

const baseURL = process.env.NODE_ENV === 'production'
  ? 'https://jose1269.integrator.host'
  : 'http://localhost:8000';

module.exports = {
  devServer: {
    port: 3000,
    proxy: {
      '/api': {
        target: baseURL,
        ws: true,
        changeOrigin: true
      }
    }
  },
  transpileDependencies: [
    'vuetify'
  ],
}