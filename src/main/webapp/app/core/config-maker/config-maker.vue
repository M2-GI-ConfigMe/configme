<template>
  <v-container id="configMaker" style="min-height: calc(100vh - 64px)" class="pt-10">
    <template v-if="isFetching">
      <v-container fluid fill-height style="background-color: rgba(255, 255, 255, 0.5)">
        <v-layout justify-center align-center>
          <v-progress-circular indeterminate size="64"></v-progress-circular>
        </v-layout>
      </v-container>
    </template>
    <template v-else>
      <v-row no-gutters>
        <v-col cols="9" class="pr-4">
          <div class="mb-4 d-flex justify-content-between align-items-center">
            <div>
              <h1 class="font-weight-bold">
                {{ selectedConfig.name }}
                <v-tooltip top color="success" v-if="isComplete">
                  <template v-slot:activator="{ on, attrs }">
                    <v-icon class="mr-2 mb-1" color="success" x-large v-on="on" v-bind="attrs"> mdi-check-circle </v-icon>
                  </template>
                  <span>Complète</span>
                </v-tooltip>
              </h1>
            </div>
            <div>
              <v-tooltip top>
                <template v-slot:activator="{ on, attrs }">
                  <v-btn
                    fab
                    color="primary"
                    small
                    class="mr-2"
                    elevation="0"
                    v-on="on"
                    v-bind="attrs"
                    @click="authenticated ? saveConfig(selectedConfig) : openLogin()"
                  >
                    <v-icon dark>mdi-content-save</v-icon>
                  </v-btn>
                </template>
                <span>Sauvegarder</span>
              </v-tooltip>
              <v-tooltip top>
                <template v-slot:activator="{ on, attrs }">
                  <v-btn fab color="secondary" small class="mr-2" elevation="0" v-on="on" v-bind="attrs">
                    <v-icon dark>mdi-share-variant</v-icon>
                  </v-btn>
                </template>
                <span>Partager</span>
              </v-tooltip>
              <v-tooltip top>
                <template v-slot:activator="{ on, attrs }">
                  <v-btn fab color="red" small elevation="0" v-on="on" v-bind="attrs" @click="deleteConfig(selectedConfig)">
                    <v-icon color="white">mdi-trash-can</v-icon>
                  </v-btn>
                </template>
                <span>Supprimer</span>
              </v-tooltip>
            </div>
          </div>
          <v-row no-gutters>
            <v-col>
              <!-- Composant PC -->
              <v-sheet
                v-for="(field, index) in configFields"
                :key="index"
                v-ripple="{ class: `primary--text` }"
                style="cursor: pointer; user-select: none"
                class="round px-6 py-1 mb-3 d-flex justify-content-between align-items-center w-75"
                elevation="1"
                :class="selectedConfig[field.key] ? 'selected-item' : ''"
                @click="openDialog(field.key)"
              >
                <div class="d-flex flex-column">
                  <span class="overline pt-2" style="line-height: 0">{{ field.displayName }}</span>
                  <span
                    class="pt-2"
                    v-html="selectedConfig[field.key] ? selectedConfig[field.key].name : 'Sélectionner un composant'"
                  ></span>
                </div>
                <v-btn
                  icon
                  class="font-weight-bold"
                  v-if="selectedConfig[field.key]"
                  color="primary2"
                  @click.stop="removeComponent(field.key)"
                >
                  <v-icon v-if="selectedConfig[field.key]" class="font-weight-bold" color="primary2">mdi-close</v-icon>
                </v-btn>
              </v-sheet>
            </v-col>
            <v-col class="pt-2 flex-grow-0 d-flex flex-column justify-content-end">
              <span class="h3 mb-0 align-self-end font-weight-bold"> Total : {{ getTotalPrice }}€ </span>
              <v-btn color="primary" rounded @click="addToCart(selectedConfig)">
                <v-icon>mdi-cart</v-icon>
                Ajouter au panier
              </v-btn>
            </v-col>
          </v-row>
        </v-col>
        <v-col class="d-flex flex-column">
          <v-card style="border-radius: 1rem; flex: 1">
            <v-card-title class="d-flex justify-content-between">
              <span class="font-weight-bold h4 mb-0">Mes Configs</span>

              <v-dialog v-model="dialog" max-width="600px" width="auto">
                <template v-slot:activator="{ on, attrs }">
                  <v-btn icon color="primary" v-if="authenticated" large v-on="on" v-bind="attrs">
                    <v-icon class="font-weight-bold">mdi-plus</v-icon>
                  </v-btn>
                  <v-btn icon color="primary" v-else @click="openLogin()">
                    <v-icon class="font-weight-bold">mdi-plus</v-icon>
                  </v-btn>
                </template>
                <v-card>
                  <v-card-title class="mb-2">
                    <span class="text-h5 font-weight-bold">Créer une Config</span>
                  </v-card-title>
                  <v-form v-model="formCreateValid">
                    <v-card-text class="pb-0">
                      <v-container class="p-0">
                        <v-row no-gutters>
                          <v-col cols="12">
                            <v-text-field
                              label="Nom*"
                              placeholder="Ma Config"
                              outlined
                              required
                              v-model="formCreateConfig.name"
                              :rules="[v => !!v || 'Le champ nom est obligatoire']"
                            ></v-text-field>
                          </v-col>
                        </v-row>
                      </v-container>
                    </v-card-text>
                    <v-card-actions>
                      <v-spacer></v-spacer>
                      <v-btn color="blue darken-1" text @click="dialog = false"> Fermer </v-btn>
                      <v-btn color="blue darken-1" text :disabled="!formCreateValid" @click="createConfig()"> OK </v-btn>
                    </v-card-actions>
                  </v-form>
                </v-card>
              </v-dialog>
            </v-card-title>
            <v-list class="pt-0">
              <v-list-item-group v-model="selectedConfigIndex" color="primary" mandatory>
                <v-list-item v-for="(config, i) in clientConfigs" :key="i">
                  <v-list-item-content>
                    <v-list-item-title v-text="config.name"></v-list-item-title>
                  </v-list-item-content>
                </v-list-item>
              </v-list-item-group>
            </v-list>
          </v-card>
        </v-col>
      </v-row>
    </template>

    <component-picker
      :activated="componentPickerDialog"
      :componentName="endpoint"
      @close="componentPickerDialog = false"
      @picked="handlePicked"
    ></component-picker>
  </v-container>
</template>

<script lang="ts" src="./config-maker.component.ts"></script>

<style>
.round {
  border-radius: 10rem;
  transition: 0.2s all ease;
}

.round:hover {
  background-color: #f3f3f3;
}

.selected-item {
  background-color: #bddcff !important;
  color: #1867c0 !important;
}
</style>
