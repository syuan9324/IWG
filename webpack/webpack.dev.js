"use strict";
const BrowserSyncPlugin = require("browser-sync-webpack-plugin");
const { VueLoaderPlugin } = require("vue-loader");

module.exports = (env, options) => {
  const devConfig = {
    //配置vue css scss loader
    module: {
      rules: [
        {
          test: /\.vue$/,
          use: ["vue-loader"],
        },
        //處理scss
        {
          test: /\.s[ac]ss$/i,
          use: ["style-loader", "css-loader", "postcss-loader", "sass-loader"],
        },
        //處理css的loader
        {
          test: /\.css$/,
          use: ["style-loader", "css-loader", "postcss-loader"],
        },
      ],
    },
    devtool: "source-map", 
    optimization: {
      moduleIds: "named",
    },
    //用BrowserSyncPlugin 自動refresh和同步各個瀏覽器
    plugins: [
      new VueLoaderPlugin(),
      new BrowserSyncPlugin(
        {
          host: "localhost",
          port: 9000,
          proxy: {
            target: `http://localhost:9060`,
            ws: true,
          },
          socket: {
            clients: {
              heartbeatTimeout: 60000,
            },
          },
        },
        {
          reload: true,
        }
      ),
    ],
  };
  return devConfig;
};
