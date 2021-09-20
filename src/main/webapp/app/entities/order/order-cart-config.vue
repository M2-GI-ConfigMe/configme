<template>
  <v-card width="400" class="order-cart-config">
    <div class="order-cart-config-remove">
      <v-btn color="error" fab x-small icon elevation="0" @click="removeConfig()">
        <v-icon>mdi-close</v-icon>
      </v-btn>
    </div>
    <v-card-text style="padding: 0 !important; z-index: 5; position: relative">
      <v-simple-table>
        <template v-slot:default>
          <thead>
            <tr>
              <th class="text-center">Produit</th>
              <th class="text-center">Prix</th>
              <th class="text-center" style="width: 10%">Actions</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="(productLine, i) in products" :key="i">
              <td class="text-center">{{ productLine.product.name }}</td>
              <td class="text-center">{{ productLine.price }} €</td>
              <td class="text-center">
                <v-btn color="error" fab x-small icon elevation="0" @click="removeProduct(productLine)">
                  <v-icon>mdi-close</v-icon>
                </v-btn>
              </td>
            </tr>
          </tbody>
        </template>
      </v-simple-table>
    </v-card-text>
    <v-card-text align="right"> Total: {{ totalPrice }} € </v-card-text>
  </v-card>
</template>

<script lang="ts" src="./order-cart-config.component.ts"></script>

<style>
.order-cart-config {
  margin-right: 50px;
  min-width: 500px;
}

.order-cart-config-remove {
  position: absolute;
  top: 0;
  right: -40px;
  width: 40px;
  height: 40px;
  display: flex;
  justify-content: center;
  align-items: center;
  box-shadow: 0 3px 1px -2px rgba(0, 0, 0, 0.2), 0 2px 2px 0 rgba(0, 0, 0, 0.14), 0 1px 5px 0 rgba(0, 0, 0, 0.12);
}
</style>
