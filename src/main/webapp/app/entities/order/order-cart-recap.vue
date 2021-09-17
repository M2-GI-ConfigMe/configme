<template>
  <div class="order-cart-recap-container">
    <div v-if="cart != null" class="order-cart-recap-configs-container">
      <order-cart-config
        v-for="(line, key) in cart.lines"
        :key="key"
        :orderLine="line"
        @configUpdated="$emit('configUpdated')"
      ></order-cart-config>
    </div>
    <div class="order-cart-recap-configs-price-container">
      <span class="order-cart-recap-configs-price">Total: {{ globalPrice }} €</span>
    </div>
  </div>
</template>
<script lang="ts" src="./order-cart-recap.component.ts"></script>

<style>
.order-cart-recap-container {
  margin-bottom: 15px;
}

.order-cart-recap-configs-container {
  display: flex;
  flex-direction: row;
  overflow-x: scroll;
  padding: 20px;
}

.order-cart-recap-configs-price-container {
  display: flex;
  justify-content: end;
  align-items: center;
  padding: 10px 0px;
}

.order-cart-recap-configs-price {
  font-size: 25px;
  color: rgba(0, 0, 0, 0.6);
}
</style>
