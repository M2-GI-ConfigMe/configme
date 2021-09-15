// src/plugins/vuetify.js

import Vue from 'vue';
import Vuetify from 'vuetify';
import 'vuetify/dist/vuetify.min.css';
import colors from 'vuetify/lib/util/colors';
import '@mdi/font/css/materialdesignicons.css';

Vue.use(Vuetify);

const opts = {
  theme: {
    themes: {
      light: {
        primary: colors.blue.base,
        success: '#67E153',
        primary2: '#1867C0',
      },
    },
  },
};

const vuetify = new Vuetify(opts);

export default vuetify;
