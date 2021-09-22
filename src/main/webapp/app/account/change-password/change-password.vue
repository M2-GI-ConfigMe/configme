<template>
  <v-row no-gutters>
    <div class="col toastify-container">
      <v-card>
        <v-card-title class="font-weight-bold">Changer le mot de passe</v-card-title>
        <v-card-text>
          <transition name="slide-x-transition">
            <v-alert type="success" v-if="success">
              <strong>Mot de passe changé !</strong>
            </v-alert>
            <v-alert type="red" v-if="error"> <strong>Une erreur est survenue !</strong> Le mot de passe n'a pas pu être changé. </v-alert>
          </transition>
          <v-form v-model="isValid" @submit.stop.prevent="changePassword()">
            <div class="form-group">
              <v-text-field
                v-model="$v.resetPassword.currentPassword.$model"
                :rules="rules.requiredField"
                :append-icon="showPass ? 'mdi-eye' : 'mdi-eye-off'"
                @click:append="showPass = !showPass"
                :type="showPass ? 'text' : 'password'"
                label="Mot de passe courant"
                placeholder="********"
                hide-details="auto"
                outlined
                required
              >
              </v-text-field>
            </div>
            <div class="form-group">
              <v-text-field
                v-model="$v.resetPassword.newPassword.$model"
                :rules="rules.passwordRules.concat(rules.requiredField)"
                :append-icon="showPass2 ? 'mdi-eye' : 'mdi-eye-off'"
                @click:append="showPass2 = !showPass2"
                :type="showPass2 ? 'text' : 'password'"
                label="Nouveau mot de passe"
                placeholder="********"
                hide-details="auto"
                outlined
                required
              >
              </v-text-field>
            </div>
            <div class="form-group">
              <v-text-field
                v-model="$v.resetPassword.confirmPassword.$model"
                :rules="rules.confirmPasswordRules.concat(rules.requiredField)"
                :append-icon="showPass3 ? 'mdi-eye' : 'mdi-eye-off'"
                @click:append="showPass3 = !showPass3"
                :type="showPass3 ? 'text' : 'password'"
                label="Confirmation du nouveau mot de passe"
                placeholder="********"
                hide-details="auto"
                outlined
                required
              >
              </v-text-field>
            </div>

            <div class="text-right">
              <v-btn :disabled="!isValid" @click.prevent="changePassword()" color="primary">Sauvegarder</v-btn>
            </div>
          </v-form>
        </v-card-text>
      </v-card>
    </div>
  </v-row>
</template>

<script lang="ts" src="./change-password.component.ts"></script>
