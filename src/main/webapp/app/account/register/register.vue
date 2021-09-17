<template>
  <v-dialog v-model="showDialog" max-width="600" id="form">
    <v-card class="p-3">
      <v-overlay :value="loading" absolute>
        <v-progress-circular indeterminate size="64"></v-progress-circular>
      </v-overlay>
      <v-card-title class="font-weight-bold text-h4 mb-4">
        <span id="register">S'inscrire</span>
      </v-card-title>
      <v-card-text>
        <v-alert type="error" v-if="errorEmailExists" class="mb-1 mt-2"> Cet email est déjà utilisée. </v-alert>
        <div class="row justify-content-center">
          <div class="col">
            <v-form v-model="isValid" v-on:submit.prevent="register()">
              <p class="font-weight-bold text-dark">Informations de connexion</p>
              <div class="form-group">
                <v-text-field
                  v-model="$v.account.email.$model"
                  :rules="rules.emailRules.concat(rules.requiredField)"
                  type="email"
                  label="E-mail"
                  placeholder="template@email.com"
                  hide-details="auto"
                  outlined
                  required
                >
                </v-text-field>
              </div>
              <div class="form-group">
                <v-text-field
                  v-model="$v.account.password.$model"
                  :rules="rules.passwordRules.concat(rules.requiredField)"
                  :append-icon="showPass ? 'mdi-eye' : 'mdi-eye-off'"
                  @click:append="showPass = !showPass"
                  :type="showPass ? 'text' : 'password'"
                  label="Mot de passe"
                  placeholder="********"
                  hide-details="auto"
                  outlined
                  required
                >
                </v-text-field>
              </div>
              <div class="form-group">
                <v-text-field
                  v-model="confirmPassword"
                  :rules="rules.confirmPasswordRules.concat(rules.requiredField)"
                  :append-icon="showPass2 ? 'mdi-eye' : 'mdi-eye-off'"
                  @click:append="showPass2 = !showPass2"
                  :type="showPass2 ? 'text' : 'password'"
                  label="Confirmation de mot de passe"
                  placeholder="********"
                  hide-details="auto"
                  outlined
                  required
                >
                </v-text-field>
              </div>

              <p class="font-weight-bold text-dark">Informations personnelles</p>
              <div class="form-group">
                <v-row v-bind:no-gutters="true">
                  <v-text-field
                    v-model="$v.account.lastName.$model"
                    :rules="rules.requiredField"
                    label="Nom"
                    placeholder="Dupont"
                    hide-details="auto"
                    outlined
                    required
                    class="mr-2"
                  >
                  </v-text-field>
                  <v-text-field
                    v-model="$v.account.firstName.$model"
                    :rules="rules.requiredField"
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
                <v-menu ref="menu" v-model="menu" :close-on-content-click="false" transition="scale-transition" offset-y min-width="auto">
                  <template v-slot:activator="{ on, attrs }">
                    <v-text-field
                      v-model="date"
                      :rules="rules.requiredField"
                      label="Date de naissance"
                      readonly
                      hide-details="auto"
                      outlined
                      required
                      v-bind="attrs"
                      v-on="on"
                    >
                    </v-text-field>
                  </template>
                  <v-date-picker
                    v-model="date"
                    :active-picker.sync="activePicker"
                    :max="new Date(Date.now() - new Date().getTimezoneOffset() * 60000).toISOString().substr(0, 10)"
                    min="1950-01-01"
                    @change="save"
                  >
                  </v-date-picker>
                </v-menu>
              </div>

              <div class="form-group">
                <v-row v-bind:no-gutters="true">
                  <v-text-field
                    v-model="$v.account.address.streetNumber.$model"
                    :rules="rules.requiredField"
                    label="N° de Rue"
                    placeholder="5"
                    hide-details="auto"
                    outlined
                    required
                    class="flex-grow-0 mr-2"
                  >
                  </v-text-field>
                  <v-text-field
                    v-model="$v.account.address.streetName.$model"
                    :rules="rules.requiredField"
                    label="Rue"
                    placeholder="Rue de l'exemple"
                    hide-details="auto"
                    outlined
                    required
                    class="flex-grow-1"
                  >
                  </v-text-field>
                </v-row>
              </div>

              <div class="form-group">
                <v-row v-bind:no-gutters="true">
                  <v-text-field
                    v-model="$v.account.address.city.$model"
                    :rules="rules.requiredField"
                    label="Ville"
                    placeholder="Grenoble"
                    hide-details="auto"
                    outlined
                    required
                  >
                  </v-text-field>
                  <v-text-field
                    v-model="$v.account.address.zipCode.$model"
                    :rules="rules.requiredField"
                    label="Code postal"
                    placeholder="38000"
                    hide-details="auto"
                    outlined
                    required
                    class="ml-2"
                  >
                  </v-text-field>
                </v-row>
              </div>

              <div class="text-right">
                <v-btn :disabled="!isValid" @click.prevent="register()" color="primary">S'inscrire</v-btn>
              </div>
            </v-form>
          </div>
        </div>
      </v-card-text>
    </v-card>
  </v-dialog>
</template>

<script lang="ts" src="./register.component.ts"></script>
