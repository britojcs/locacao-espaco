const path = require("path");

module.exports = {
  devServer: {
    port: 3000
  },
  transpileDependencies: [
    "vuetify"
  ],
  productionSourceMap: false,
  // outputDir: path.resolve(__dirname, "../vue-expenses-api/wwwroot"),
  // assetsDir: "../../vue-expenses-api/wwwroot/static"
}