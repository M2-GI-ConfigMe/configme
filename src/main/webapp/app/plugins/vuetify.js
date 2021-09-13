// src/plugins/vuetify.js

import Vue from 'vue';
import Vuetify from 'vuetify';
import 'vuetify/dist/vuetify.min.css';

Vue.use(Vuetify);

const opts = {
  /*theme: {
      themes: {
        light: {
          primary: '#2196F3',
        },
      },
    },*/
};

const vuetify = new Vuetify(opts);

export default vuetify;
