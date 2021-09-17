<template>
  <v-stepper v-model="state" elevation="0">
    <v-stepper-header>
      <v-stepper-step :complete="state > 1" step="1"> Récapitulatif de la commande </v-stepper-step>
      <v-divider></v-divider>
      <v-stepper-step :complete="state > 2" step="2"> Addresse de livraison </v-stepper-step>
      <v-divider></v-divider>
      <v-stepper-step step="3"> Achat </v-stepper-step>
    </v-stepper-header>
    <v-stepper-items>
      <v-stepper-content step="1" class="child-visible text-right">
        <order-cart-recap v-if="cart" :cart="cart" @configUpdated="retrieveCart()"></order-cart-recap>
        <v-btn color="primary" @click="goToDeliveryAddress"> Valider </v-btn>
      </v-stepper-content>
      <v-stepper-content step="2" class="text-right">
        <order-cart-address v-if="cart" :cart="cart" @configUpdated="retrieveCart()"></order-cart-address>
        <v-btn color="primary" @click="goToPayment"> Valider </v-btn>
      </v-stepper-content>
      <v-stepper-content step="3" class="text-right">
        <v-btn color="primary"> Payer </v-btn>
      </v-stepper-content>
    </v-stepper-items>
  </v-stepper>
</template>
<script lang="ts" src="./order-cart-process.component.ts"></script>
<style>
.v-stepper__wrapper {
  overflow: visible;
}
</style>
