// module.exports = {
//   devServer: {
//     proxy: {
//       '/api': {
//         target: 'http://localhost:8080',
//         changeOrigin: true,
//         pathRewrite: { '^/api': '' },
//       },
//     },
//   },
// };


module.exports = {
  devServer: process.env.NODE_ENV === 'development' ? {
    proxy: {
      '/api': {
        target: 'http://localhost:8080',
        changeOrigin: true,
        pathRewrite: { '^/api': '' },
      },
    },
  } : {},
};
