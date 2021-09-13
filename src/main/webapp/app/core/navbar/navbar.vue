<template>
  <div>
    <v-app-bar app>
      <v-toolbar-title variant="primary" class="font-weight-bold text-info">
        <router-link to="/" tag="span" style="cursor: pointer">
          {{ appTitle }}
        </router-link>
      </v-toolbar-title>
      <v-spacer></v-spacer>
      <v-toolbar-items class="hidden-xs-only">
        <v-btn text v-on:click="openLogin()" v-if="!authenticated"> Connexion </v-btn>
        <v-btn
          text
          v-if="authenticated"
          v-on:click="$router.push('/account/settings')"
          :color="$route.path == '/account/settings' ? 'info' : ''"
        >
          Mon Compte
        </v-btn>
        <v-menu offset-y v-if="authenticated && hasAnyAuthority('ROLE_ADMIN')">
          <template v-slot:activator="{ on, attrs }">
            <v-btn text v-bind="attrs" v-on="on" :color="hasItemActive(entitiesItems) ? 'info' : ''"> Entités </v-btn>
          </template>
          <v-list>
            <v-list-item
              v-for="(item, index) in entitiesItems"
              :key="index"
              @click="$router.push(item.path)"
              :class="item.path === $route.path ? 'info text-white' : ''"
            >
              <v-list-item-title>{{ item.title }}</v-list-item-title>
            </v-list-item>
          </v-list>
        </v-menu>
      </v-toolbar-items>
      <v-toolbar-items>
        <v-menu offset-y v-if="authenticated && hasAnyAuthority('ROLE_ADMIN')">
          <template v-slot:activator="{ on, attrs }">
            <v-btn text v-bind="attrs" v-on="on" :color="hasItemActive(adminItems) ? 'info' : ''"> Administration </v-btn>
          </template>
          <v-list>
            <v-list-item
              v-for="(item, index) in adminItems"
              :key="index"
              @click="$router.push(item.path)"
              :class="item.path === $route.path ? 'info text-white' : ''"
            >
              <v-list-item-title>{{ item.title }}</v-list-item-title>
            </v-list-item>
          </v-list>
        </v-menu>
      </v-toolbar-items>
      <v-btn text v-if="authenticated" v-on:click="logout()"> Déconnexion </v-btn>
    </v-app-bar>
  </div>
</template>
