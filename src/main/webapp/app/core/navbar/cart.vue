<template>
  <v-menu v-model="showCart" :close-on-content-click="false" offset-y min-width="300" max-width="300" max-height="500">
    <template v-slot:activator="{ on: menu, attrs }">
      <v-badge color="primary" :value="cart.length > 0" offset-x="20" offset-y="20" :content="cart.length" class="font-weight-bold">
        <v-tooltip bottom :value="showTooltip" close-delay="10" :color="showTooltip ? 'success' : 'transparent'">
          <template v-slot:activator="{ on: tooltip }">
            <v-btn :color="showCart ? 'primary' : 'black'" v-bind="{ ...attrs }" v-on="{ ...menu, ...tooltip }" id="cartbtn" icon>
              <v-icon>mdi-cart</v-icon>
            </v-btn>
          </template>
          <span v-if="showTooltip">Panier mis à jour !</span>
        </v-tooltip>
      </v-badge>
    </template>

    <v-card>
      <v-card-title>
        Votre Panier
        <v-spacer></v-spacer>
        <v-chip color="primary" small> {{ cart.length }} Items </v-chip>
      </v-card-title>

      <v-divider class="my-0"></v-divider>
      <v-card-text class="p-0">
        <v-expansion-panels accordion v-if="cart.length > 0" style="max-height: 300px; overflow-y: auto">
          <v-expansion-panel v-for="(config, index) in cart" :key="index">
            <v-expansion-panel-header class="py-0 pr-4 pl-2">
              <div class="d-flex justify-content-between align-items-center">
                <div class="d-flex align-items-center">
                  <v-btn icon small color="red" @click.stop="removeFromCart(index)" class="mr-2">
                    <v-icon small>mdi-close</v-icon>
                  </v-btn>
                  <span style="max-width: 120px">{{ config.name }}</span>
                </div>
                <v-chip small class="mx-2" color="#F5F5F5"> {{ getConfigPrice(config) }}€ </v-chip>
              </div>
            </v-expansion-panel-header>
            <v-expansion-panel-content class="pb-1">
              <ul class="mb-0 pl-1">
                <li
                  v-for="(item, index) in Object.fromEntries(Object.entries(config).filter(([key]) => !key.includes('name')))"
                  :key="index"
                  class="text-caption text-secondary"
                >
                  {{ item.name }} - {{ item.price }}€
                </li>
              </ul>
            </v-expansion-panel-content>
          </v-expansion-panel>
        </v-expansion-panels>
        <div v-else class="px-4 py-8 text-center">
          <div>
            <v-icon x-large>mdi-cart-off</v-icon>
          </div>
          <span class="text-caption">Votre panier est vide.</span>
        </div>
      </v-card-text>
      <v-card-actions>
        <span class="ml-2">Total : {{ totalPrice }}€</span>
        <v-spacer></v-spacer>
        <v-btn color="primary" text :disabled="!cart.length > 0" @click="pay()"> Payer </v-btn>
      </v-card-actions>
    </v-card>
  </v-menu>
</template>

<script lang="ts" src="./cart.component.ts"></script>
