import { Module } from 'vuex';

export const cartStore: Module<any, any> = {
  state: {
    cart: JSON.parse(localStorage.getItem('cart')) || [],
  },
  getters: {
    cart: state => state.cart,
  },
  mutations: {
    addToCart(state, item) {
      state.cart.push(item);
      localStorage.setItem('cart', JSON.stringify(state.cart));
    },
    removeFromCart(state, index) {
      state.cart.splice(index, 1);
      localStorage.setItem('cart', JSON.stringify(state.cart));
    },
  },
};
