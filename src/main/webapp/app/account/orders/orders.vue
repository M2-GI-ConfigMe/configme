<template>
  <v-row no-gutters class="h-100">
    <v-col cols="12" class="h-100 d-flex flex-column">
      <v-card class="d-flex flex-column">
        <v-card-title class="font-weight-bold">Mes commandes</v-card-title>
        <v-card-text class="px-0 pb-0 h-100" style="min-height: 0">
          <v-expansion-panels
            v-if="orders.length > 0"
            hover
            tile
            class="elevation-0 h-100"
            style="min-height: 0; overflow-y: auto"
            flat
            focusable
          >
            <v-expansion-panel v-for="(order, Oindex) in orders" :key="Oindex" style="border-top: 1px solid lightgray">
              <v-expansion-panel-header>
                <div class="d-flex justify-content-between align-items-center" style="flex: 1">
                  <div class="text-h5 d-flex flex-column">
                    <div>
                      <span>Commande</span>
                      <span class="font-weight-bold"> #{{ order.id }}</span>
                    </div>
                    <span class="caption">Le {{ order.validatedAt }}</span>
                  </div>
                  <div class="d-flex flex-column mr-2">
                    <span class="mb-1 align-self-end">{{ getOrderPrice(order) }}€</span>
                    <v-chip :color="status[order.status].color" class="font-weight-bold text-white" small>
                      {{ status[order.status].name }}
                    </v-chip>
                  </div>
                </div>
              </v-expansion-panel-header>
              <v-expansion-panel-content class="pt-3">
                <v-expansion-panels>
                  <v-expansion-panel v-for="(line, Lindex) in order.lines" :key="Lindex">
                    <v-expansion-panel-header>
                      <div class="d-flex justify-content-between" style="flex: 1">
                        <span>Config {{ Lindex + 1 }}</span>
                        <span>{{ getConfigPrice(line.config) }}€</span>
                      </div>
                    </v-expansion-panel-header>
                    <v-expansion-panel-content class="pt-1">
                      <ul>
                        <li v-for="(item, Iindex) in getFilteredConfig(line.config)" :key="Iindex">
                          {{ item.product.name }} - {{ item.price }}€
                        </li>
                      </ul>
                    </v-expansion-panel-content>
                  </v-expansion-panel>
                </v-expansion-panels>
              </v-expansion-panel-content>
            </v-expansion-panel>
          </v-expansion-panels>
          <div v-else class="pa-4 text-center">
            <v-icon class="d-block mb-2" color="#C0C0C0" x-large> mdi-emoticon-sad </v-icon>
            <span class="caption">Vous n'avez pas encore commandé chez nous...</span>
          </div>
        </v-card-text>
      </v-card>
    </v-col>
  </v-row>
</template>

<script lang="ts" src="./orders.component.ts"></script>
