<template>
  <v-container>
    <v-stepper v-model="state" elevation="1">
      <v-overlay v-if="loading" absolute class="py-4" style="z-index: 2">
        <v-progress-circular indeterminate :size="70" :width="7" color="primary"></v-progress-circular>
      </v-overlay>
      <v-stepper-header>
        <v-stepper-step :complete="state > 1" step="1" :editable="state != 4"> Récapitulatif de la commande </v-stepper-step>
        <v-divider></v-divider>
        <v-stepper-step :complete="state > 2" step="2" :editable="state != 4"> Addresse de livraison </v-stepper-step>
        <v-divider></v-divider>
        <v-stepper-step step="3" :complete="state > 3"> Achat </v-stepper-step>
        <v-divider></v-divider>
        <v-stepper-step step="4"> Confirmation </v-stepper-step>
      </v-stepper-header>
      <v-stepper-items>
        <v-stepper-content step="1" class="text-right px-0 pt-0">
          <v-alert color="red lighten-2" dark class="text-left" v-if="errorMessage.length">
            {{ errorMessage }}
          </v-alert>
          <order-cart-recap v-if="cart" :cart="cart" @configUpdated="retrieveCart()"></order-cart-recap>
          <div class="px-4">
            <v-btn color="primary" @click="goToDeliveryAddress"> Valider </v-btn>
          </div>
        </v-stepper-content>
        <v-stepper-content step="2" class="text-right px-0 pt-0">
          <order-cart-address v-if="cart" :cart="cart" @configUpdated="retrieveCart()"></order-cart-address>
          <div class="px-4">
            <v-btn color="primary" @click="goToPayment"> Valider </v-btn>
          </div>
        </v-stepper-content>
        <v-stepper-content step="3" class="text-right px-0 pt-4">
          <div class="px-4">
            <v-btn color="primary" @click="pay()"> Payer </v-btn>
          </div>
        </v-stepper-content>
        <v-stepper-content step="4" class="px-0 pt-4">
          <order-cart-confirmation :order="confirmedOrder"></order-cart-confirmation>
        </v-stepper-content>
      </v-stepper-items>
    </v-stepper>
  </v-container>
</template>
<script lang="ts" src="./order-cart-process.component.ts"></script>
<style>
.v-stepper__wrapper {
  overflow: visible;
}
</style>
