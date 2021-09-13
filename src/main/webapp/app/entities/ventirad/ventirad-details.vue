<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="ventirad">
        <h2 class="jh-entity-heading" data-cy="ventiradDetailsHeading">
          <span v-text="$t('configmeApp.ventirad.detail.title')">Ventirad</span> {{ ventirad.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('configmeApp.ventirad.rangeFanSpeed')">Range Fan Speed</span>
          </dt>
          <dd>
            <span>{{ ventirad.rangeFanSpeed }}</span>
          </dd>
          <dt>
            <span v-text="$t('configmeApp.ventirad.noise')">Noise</span>
          </dt>
          <dd>
            <span>{{ ventirad.noise }}</span>
          </dd>
          <dt>
            <span v-text="$t('configmeApp.ventirad.hasThermalPaste')">Has Thermal Paste</span>
          </dt>
          <dd>
            <span>{{ ventirad.hasThermalPaste }}</span>
          </dd>
          <template v-if="ventirad.dimension">
            <dt>
              <span v-text="$t('configmeApp.dimension.height')">Height</span>
            </dt>
            <dd>
              <span>{{ ventirad.dimension.height }} mm</span>
            </dd>
            <dt>
              <span v-text="$t('configmeApp.dimension.width')">Width</span>
            </dt>
            <dd>
              <span>{{ ventirad.dimension.width }} mm</span>
            </dd>
            <dt>
              <span v-text="$t('configmeApp.dimension.length')">Length</span>
            </dt>
            <dd>
              <span>{{ ventirad.dimension.length }} mm</span>
            </dd>
          </template>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link v-if="ventirad.id" :to="{ name: 'VentiradEdit', params: { ventiradId: ventirad.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./ventirad-details.component.ts"></script>
