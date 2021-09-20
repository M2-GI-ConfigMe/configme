<template>
  <v-card class="p-3" style="margin-bottom: 15px">
    <v-card-title class="font-weight-bold text-h4 mb-4">
      <span id="register">Addresse de livraison</span>
    </v-card-title>
    <v-card-text>
      <div class="row justify-content-center">
        <div class="col">
          <v-form v-on:submit.prevent>
            <div class="form-group">
              <v-row v-bind:no-gutters="true">
                <v-text-field
                  v-model="cart.deliveryAddress.lastName"
                  label="Nom"
                  placeholder="Dupont"
                  hide-details="auto"
                  outlined
                  required
                >
                </v-text-field>
                <v-spacer class="mx-2"></v-spacer>
                <v-text-field
                  v-model="cart.deliveryAddress.firstName"
                  label="Prénom"
                  placeholder="François"
                  hide-details="auto"
                  outlined
                  required
                >
                </v-text-field>
              </v-row>
            </div>
            <div class="form-group">
              <v-row v-bind:no-gutters="true">
                <v-text-field
                  v-model="cart.deliveryAddress.streetNumber"
                  label="N° de Rue"
                  placeholder="5"
                  hide-details="auto"
                  outlined
                  required
                >
                </v-text-field>
                <v-spacer class="mx-2"></v-spacer>
                <v-text-field
                  v-model="cart.deliveryAddress.streetName"
                  label="Rue"
                  placeholder="Rue de l'exemple"
                  hide-details="auto"
                  outlined
                  required
                >
                </v-text-field>
              </v-row>
            </div>
            <div class="form-group">
              <v-row v-bind:no-gutters="true">
                <v-text-field
                  v-model="cart.deliveryAddress.city"
                  label="Ville"
                  placeholder="Grenoble"
                  hide-details="auto"
                  outlined
                  required
                >
                </v-text-field>
                <v-spacer class="mx-2"></v-spacer>
                <v-text-field
                  v-model="cart.deliveryAddress.zipCode"
                  label="Code postal"
                  placeholder="38000"
                  hide-details="auto"
                  outlined
                  required
                >
                </v-text-field>
              </v-row>
            </div>
          </v-form>
        </div>
      </div>
    </v-card-text>
  </v-card>
</template>
<script lang="ts" src="./order-cart-address.component.ts"></script>

<style></style>
