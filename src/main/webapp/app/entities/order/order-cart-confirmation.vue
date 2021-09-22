<template>
  <div class="h-100 px-4 pt-2" v-if="order">
    <div class="text-center">
      <span class="text-h3 mb-2 font-weight-bold d-block">Félicitations !</span>
      <v-icon color="success" :size="128">mdi-check-circle</v-icon>
    </div>
    <div class="text-center mb-8">Votre commande n°{{ order.id }} a été validée !</div>
    <v-btn width="100%" color="primary" @click="$router.push('/')">Retour à l'accueil</v-btn>
  </div>
</template>

<script lang="ts" src="./order-cart-confirmation.component.ts"></script>
