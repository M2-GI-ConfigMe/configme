<template>
  <div class="modal-body">
    <div class="row justify-content-center">
      <div class="col">
        <v-form v-model="isValid" v-on:submit.prevent="register()">
          <p class="font-weight-bold">Informations de connexion</p>
          <div class="form-group">
            <v-text-field
              v-model="informations.email"
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
              v-model="informations.password"
              :rules="rules.passwordRules.concat(rules.requiredField)"
              type="password"
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
              type="password"
              label="Confirmation de mot de passe"
              placeholder="********"
              hide-details="auto"
              outlined
              required
            >
            </v-text-field>
          </div>

          <p class="font-weight-bold">Informations personnelles</p>
          <div class="form-group">
            <v-row v-bind:no-gutters="true">
              <v-text-field
                v-model="informations.lastName"
                :rules="rules.requiredField"
                label="Nom"
                placeholder="Dupont"
                hide-details="auto"
                outlined
                required
              >
              </v-text-field>
              <v-spacer class="mx-2"></v-spacer>
              <v-text-field
                v-model="informations.firstName"
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
                v-model="informations.streetNumber"
                :rules="rules.requiredField"
                label="N° de Rue"
                placeholder="5"
                hide-details="auto"
                outlined
                required
              >
              </v-text-field>
              <v-spacer class="mx-2"></v-spacer>
              <v-text-field
                v-model="informations.streetName"
                :rules="rules.requiredField"
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
                v-model="informations.city"
                :rules="rules.requiredField"
                label="Ville"
                placeholder="Grenoble"
                hide-details="auto"
                outlined
                required
              >
              </v-text-field>
              <v-spacer class="mx-2"></v-spacer>
              <v-text-field
                v-model="informations.zipCode"
                :rules="rules.requiredField"
                label="Code postal"
                placeholder="38000"
                hide-details="auto"
                outlined
                required
              >
              </v-text-field>
            </v-row>
          </div>

          <button type="submit" :disabled="!isValid" class="btn btn-primary" v-text="$t('register.form.button')" data-cy="submit">
            Register
          </button>
        </v-form>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./register.component.ts"></script>
