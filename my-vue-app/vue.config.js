module.exports = {
  devServer: {
    proxy: {
      '/api': {
        // target: 'http://localhost:8080',
        target: 'https://vue-backend-project.onrender.com',
        changeOrigin: true,
        pathRewrite: { '^/api': '' },
      },
    },
  },
};
