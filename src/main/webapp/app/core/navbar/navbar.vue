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
  <!--
  <b-navbar data-cy="navbar" toggleable="md" type="dark" class="jh-navbar">
    <b-navbar-brand class="logo" b-link to="/">
      <span class="logo-img"></span>
      <span v-text="$t('global.title')" class="navbar-title">configme</span> <span class="navbar-version">{{ version }}</span>
    </b-navbar-brand>
    <b-navbar-toggle
      right
      class="jh-navbar-toggler d-lg-none"
      href="javascript:void(0);"
      data-toggle="collapse"
      target="header-tabs"
      aria-expanded="false"
      aria-label="Toggle navigation"
    >
      <font-awesome-icon icon="bars" />
    </b-navbar-toggle>

    <b-collapse is-nav id="header-tabs">
      <b-navbar-nav class="ml-auto">
        <b-nav-item to="/" exact>
          <span>
            <font-awesome-icon icon="home" />
            <span v-text="$t('global.menu.home')">Home</span>
          </span>
        </b-nav-item>
        <b-nav-item-dropdown right id="entity-menu" v-if="authenticated" active-class="active" class="pointer" data-cy="entity">
          <span slot="button-content" class="navbar-dropdown-menu">
            <font-awesome-icon icon="th-list" />
            <span class="no-bold" v-text="$t('global.menu.entities.main')">Entities</span>
          </span>
          <b-dropdown-item to="/address">
            <font-awesome-icon icon="asterisk" />
            <span v-text="$t('global.menu.entities.address')">Address</span>
          </b-dropdown-item>
          <b-dropdown-item to="/product">
            <font-awesome-icon icon="asterisk" />
            <span v-text="$t('global.menu.entities.product')">Product</span>
          </b-dropdown-item>
          <b-dropdown-item to="/order">
            <font-awesome-icon icon="asterisk" />
            <span v-text="$t('global.menu.entities.order')">Order</span>
          </b-dropdown-item>
          <b-dropdown-item to="/order-line">
            <font-awesome-icon icon="asterisk" />
            <span v-text="$t('global.menu.entities.orderLine')">Order Line</span>
          </b-dropdown-item>
          <b-dropdown-item to="/client-config">
            <font-awesome-icon icon="asterisk" />
            <span v-text="$t('global.menu.entities.clientConfig')">Client Config</span>
          </b-dropdown-item>
          <b-dropdown-item to="/cpu">
            <font-awesome-icon icon="asterisk" />
            <span v-text="$t('global.menu.entities.cpu')">Cpu</span>
          </b-dropdown-item>
          <b-dropdown-item to="/gpu">
            <font-awesome-icon icon="asterisk" />
            <span v-text="$t('global.menu.entities.gpu')">Gpu</span>
          </b-dropdown-item>
          <b-dropdown-item to="/hard-drive">
            <font-awesome-icon icon="asterisk" />
            <span v-text="$t('global.menu.entities.hardDrive')">Hard Drive</span>
          </b-dropdown-item>
          <b-dropdown-item to="/ram">
            <font-awesome-icon icon="asterisk" />
            <span v-text="$t('global.menu.entities.ram')">Ram</span>
          </b-dropdown-item>
          <b-dropdown-item to="/psu">
            <font-awesome-icon icon="asterisk" />
            <span v-text="$t('global.menu.entities.psu')">Psu</span>
          </b-dropdown-item>
          <b-dropdown-item to="/ventirad">
            <font-awesome-icon icon="asterisk" />
            <span v-text="$t('global.menu.entities.ventirad')">Ventirad</span>
          </b-dropdown-item>
          <b-dropdown-item to="/dimension">
            <font-awesome-icon icon="asterisk" />
            <span v-text="$t('global.menu.entities.dimension')">Dimension</span>
          </b-dropdown-item>
          <b-dropdown-item to="/mbe">
            <font-awesome-icon icon="asterisk" />
            <span v-text="$t('global.menu.entities.mbe')">Mbe</span>
          </b-dropdown-item>
          <b-dropdown-item to="/computer-case">
            <font-awesome-icon icon="asterisk" />
            <span v-text="$t('global.menu.entities.computerCase')">Computer Case</span>
          </b-dropdown-item>
          <!-- jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here 
        </b-nav-item-dropdown>
        <b-nav-item-dropdown
          right
          id="admin-menu"
          v-if="hasAnyAuthority('ROLE_ADMIN') && authenticated"
          :class="{ 'router-link-active': subIsActive('/admin') }"
          active-class="active"
          class="pointer"
          data-cy="adminMenu"
        >
          <span slot="button-content" class="navbar-dropdown-menu">
            <font-awesome-icon icon="users-cog" />
            <span class="no-bold" v-text="$t('global.menu.admin.main')">Administration</span>
          </span>
          <b-dropdown-item to="/admin/user-management" active-class="active">
            <font-awesome-icon icon="users" />
            <span v-text="$t('global.menu.admin.userManagement')">User management</span>
          </b-dropdown-item>
          <b-dropdown-item to="/admin/metrics" active-class="active">
            <font-awesome-icon icon="tachometer-alt" />
            <span v-text="$t('global.menu.admin.metrics')">Metrics</span>
          </b-dropdown-item>
          <b-dropdown-item to="/admin/health" active-class="active">
            <font-awesome-icon icon="heart" />
            <span v-text="$t('global.menu.admin.health')">Health</span>
          </b-dropdown-item>
          <b-dropdown-item to="/admin/configuration" active-class="active">
            <font-awesome-icon icon="cogs" />
            <span v-text="$t('global.menu.admin.configuration')">Configuration</span>
          </b-dropdown-item>
          <b-dropdown-item to="/admin/logs" active-class="active">
            <font-awesome-icon icon="tasks" />
            <span v-text="$t('global.menu.admin.logs')">Logs</span>
          </b-dropdown-item>
          <b-dropdown-item v-if="openAPIEnabled" to="/admin/docs" active-class="active">
            <font-awesome-icon icon="book" />
            <span v-text="$t('global.menu.admin.apidocs')">API</span>
          </b-dropdown-item>
        </b-nav-item-dropdown>
        <b-nav-item-dropdown id="languagesnavBarDropdown" right v-if="languages && Object.keys(languages).length > 1">
          <span slot="button-content">
            <font-awesome-icon icon="flag" />
            <span class="no-bold" v-text="$t('global.menu.language')">Language</span>
          </span>
          <b-dropdown-item
            v-for="(value, key) in languages"
            :key="`lang-${key}`"
            v-on:click="changeLanguage(key)"
            :class="{ active: isActiveLanguage(key) }"
          >
            {{ value.name }}
          </b-dropdown-item>
        </b-nav-item-dropdown>
        <b-nav-item-dropdown
          right
          href="javascript:void(0);"
          id="account-menu"
          :class="{ 'router-link-active': subIsActive('/account') }"
          active-class="active"
          class="pointer"
          data-cy="accountMenu"
        >
          <span slot="button-content" class="navbar-dropdown-menu">
            <font-awesome-icon icon="user" />
            <span class="no-bold" v-text="$t('global.menu.account.main')"> Account </span>
          </span>
          <b-dropdown-item data-cy="settings" to="/account/settings" tag="b-dropdown-item" v-if="authenticated" active-class="active">
            <font-awesome-icon icon="wrench" />
            <span v-text="$t('global.menu.account.settings')">Settings</span>
          </b-dropdown-item>
          <b-dropdown-item data-cy="passwordItem" to="/account/password" tag="b-dropdown-item" v-if="authenticated" active-class="active">
            <font-awesome-icon icon="lock" />
            <span v-text="$t('global.menu.account.password')">Password</span>
          </b-dropdown-item>
          <b-dropdown-item data-cy="logout" v-if="authenticated" v-on:click="logout()" id="logout" active-class="active">
            <font-awesome-icon icon="sign-out-alt" />
            <span v-text="$t('global.menu.account.logout')">Sign out</span>
          </b-dropdown-item>
          <b-dropdown-item data-cy="login" v-if="!authenticated" v-on:click="openLogin()" id="login" active-class="active">
            <font-awesome-icon icon="sign-in-alt" />
            <span v-text="$t('global.menu.account.login')">Sign in</span>
          </b-dropdown-item>
          <b-dropdown-item
            data-cy="register"
            to="/register"
            tag="b-dropdown-item"
            id="register"
            v-if="!authenticated"
            active-class="active"
          >
            <font-awesome-icon icon="user-plus" />
            <span v-text="$t('global.menu.account.register')">Register</span>
          </b-dropdown-item>
        </b-nav-item-dropdown>
      </b-navbar-nav>
    </b-collapse>
  </b-navbar>-->
</template>

<script lang="ts" src="./navbar.component.ts"></script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
/* ==========================================================================
    Navbar
    ========================================================================== */
.navbar-version {
  font-size: 10px;
  color: #ccc;
}

.jh-navbar {
  background-color: #353d47;
  padding: 0.2em 1em;
}

.jh-navbar .profile-image {
  margin: -10px 0px;
  :40px ;
  width: 40px;
  border-radius: 50%;
}

.jh-navbar .dropdown-item.active,
.jh-navbar .dropdown-item.active:focus,
.jh-navbar .dropdown-item.active:hover {
  background-color: #353d47;
}

.jh-navbar .dropdown-toggle::after {
  margin-left: 0.15em;
}

.jh-navbar ul.navbar-nav {
  padding: 0.5em;
}

.jh-navbar .navbar-nav .nav-item {
  margin-left: 1.5rem;
}

.jh-navbar a.nav-link,
.jh-navbar .no-bold {
  font-weight: 400;
}

.jh-navbar .jh-navbar-toggler {
  color: #ccc;
  font-size: 1.5em;
  padding: 10px;
}

.jh-navbar .jh-navbar-toggler:hover {
  color: #fff;
}

@media screen and (min-width: 768px) {
  .jh-navbar-toggler {
    display: none;
  }
}

@media screen and (min-width: 768px) and (max-width: 1150px) {
  span span {
    display: none;
  }
}

.navbar-title {
  display: inline-block;
  vertical-align: middle;
  color: white;
}

/* ==========================================================================
    Logo styles
    ========================================================================== */
.navbar-brand.logo {
  padding: 5px 15px;
}

.logo .logo-img {
  height: 45px;
  display: inline-block;
  vertical-align: middle;
  width: 70px;
}

.logo-img {
  height: 100%;
  background: url('../../../content/images/logo-jhipster.png') no-repeat center center;
  background-size: contain;
  width: 100%;
  filter: drop-shadow(0 0 0.05rem white);
}
</style>
