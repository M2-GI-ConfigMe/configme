<template>
  <v-row no-gutters>
    <div class="col toastify-container">
      <v-card>
        <v-card-title class="font-weight-bold"> Mes Informations </v-card-title>
        <v-card-text>
          <transition name="slide-x-transition">
            <v-alert type="success" v-if="success == 'OK'">
              <strong>Informations sauvegardées !</strong>
            </v-alert>
          </transition>

          <v-form v-model="isValid" @submit.stop.prevent="save()">
            <div class="form-group">
              <v-text-field
                v-model="$v.settingsAccount.email.$model"
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
              <v-row v-bind:no-gutters="true">
                <v-col>
                  <v-text-field
                    v-model="$v.settingsAccount.firstName.$model"
                    :rules="rules.firstNameRules.concat(rules.requiredField)"
                    label="Prénom"
                    placeholder="François"
                    hide-details="auto"
                    outlined
                    required
                  >
                  </v-text-field>
                </v-col>
                <v-col>
                  <v-text-field
                    class="ml-4"
                    v-model="$v.settingsAccount.lastName.$model"
                    :rules="rules.lastNameRules.concat(rules.requiredField)"
                    label="Nom"
                    placeholder="Dupont"
                    hide-details="auto"
                    outlined
                    required
                  >
                  </v-text-field>
                </v-col>
              </v-row>
            </div>
            <div class="form-group">
              <v-row v-bind:no-gutters="true">
                <v-col cols="3">
                  <v-text-field
                    v-model="$v.settingsAccount.address.streetNumber.$model"
                    :rules="rules.streetNumberRules.concat(rules.requiredField)"
                    label="N° de Rue"
                    placeholder="5"
                    hide-details="auto"
                    outlined
                    required
                  >
                  </v-text-field>
                </v-col>
                <v-col>
                  <v-text-field
                    class="ml-4"
                    v-model="$v.settingsAccount.address.streetName.$model"
                    :rules="rules.streetNameRules.concat(rules.requiredField)"
                    label="Rue"
                    placeholder="Rue de l'exemple"
                    hide-details="auto"
                    outlined
                    required
                  >
                  </v-text-field>
                </v-col>
              </v-row>
            </div>

            <div class="form-group">
              <v-row v-bind:no-gutters="true">
                <v-col>
                  <v-text-field
                    v-model="$v.settingsAccount.address.city.$model"
                    :rules="rules.cityRules.concat(rules.requiredField)"
                    label="Ville"
                    placeholder="Grenoble"
                    hide-details="auto"
                    outlined
                    required
                  >
                  </v-text-field>
                </v-col>
                <v-col>
                  <v-text-field
                    class="ml-4"
                    v-model="$v.settingsAccount.address.zipCode.$model"
                    :rules="rules.zipCodeRules.concat(rules.requiredField)"
                    label="Code postal"
                    placeholder="38000"
                    hide-details="auto"
                    outlined
                    required
                  >
                  </v-text-field>
                </v-col>
              </v-row>
            </div>
            <!-- <div class="form-group" v-if="languages && Object.keys(languages).length > 1">
            <label for="langKey" v-text="$t('settings.form.language')">Language</label>
            <select class="form-control" id="langKey" name="langKey" v-model="settingsAccount.langKey" data-cy="langKey">
              <option v-for="(language, key) in languages" :value="key" :key="`lang-${key}`">{{ language.name }}</option>
            </select>
          </div> -->
            <v-btn color="primary" :disabled="!isValid" @click="save()">Sauvegarder</v-btn>
          </v-form>
        </v-card-text>
        <v-divider></v-divider>
        <v-card-title class="font-weight-bold">Mon Compte</v-card-title>
        <v-card-text>
          <v-dialog v-model="dialog" width="400">
            <template v-slot:activator="{ on, attrs }">
              <v-btn color="error" v-bind="attrs" v-on="on">Supprimer mon compte</v-btn>
            </template>
            <v-card>
              <v-card-title class="text-h5"> Supprimer </v-card-title>

              <v-card-text> Êtes-vous sûr de vouloir supprimer votre compte ? </v-card-text>

              <v-card-actions>
                <v-btn color="secondary" text @click="dialog = false"> Non </v-btn>
                <v-spacer></v-spacer>
                <v-btn color="error" text @click="deleteAccount()"> Oui </v-btn>
              </v-card-actions>
            </v-card>
          </v-dialog>
        </v-card-text>
      </v-card>
    </div>
  </v-row>
</template>

<script lang="ts" src="./settings.component.ts"></script>
