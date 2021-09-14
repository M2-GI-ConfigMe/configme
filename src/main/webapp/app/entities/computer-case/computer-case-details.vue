<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="computerCase">
        <h2 class="jh-entity-heading titre-container-entity" data-cy="computerCaseDetailsHeading">
          <div><span v-text="$t('configmeApp.computerCase.detail.title')">ComputerCase</span> {{ computerCase.id }}</div>
          <div>
            <button class="btn btn-danger btn-sm deactivated" v-on:click="setActive(computerCase, true)" v-if="!computerCase.isActive">
              Activer
            </button>
            <button class="btn btn-success btn-sm" v-on:click="setActive(computerCase, false)" v-if="computerCase.isActive">
              Désactiver
            </button>
          </div>
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('configmeApp.product.name')">Name</span>
          </dt>
          <dd>
            <span>{{ computerCase.name }}</span>
          </dd>
          <dt>
            <span v-text="$t('configmeApp.product.price')">Price</span>
          </dt>
          <dd>
            <span>{{ computerCase.price }} €</span>
          </dd>
          <dt>
            <span v-text="$t('configmeApp.product.discount')">Discount</span>
          </dt>
          <dd>
            <span>{{ Math.floor((1 - computerCase.discount) * 100) }}%</span>
          </dd>
          <dt>
            <span v-text="$t('configmeApp.product.stock')">Stock</span>
          </dt>
          <dd>
            <span>{{ computerCase.stock }}</span>
          </dd>
          <dt>
            <span v-text="$t('configmeApp.product.img')">Image</span>
          </dt>
          <dd>
            <span><img :src="computerCase.img" alt="photot de l'article" /></span>
          </dd>
          <dt>
            <span v-text="$t('configmeApp.product.brand')">Marque</span>
          </dt>
          <dd>
            <span>{{ computerCase.brand }}</span>
          </dd>
          <dt>
            <span v-text="$t('configmeApp.computerCase.type')">Type</span>
          </dt>
          <dd>
            <span v-text="$t('configmeApp.CaseType.' + computerCase.type)">{{ computerCase.type }}</span>
          </dd>
          <dt>
            <span v-text="$t('configmeApp.computerCase.formats')">Formats</span>
          </dt>
          <dd>
            <span>{{ computerCase.formats }}</span>
          </dd>
          <dt>
            <span v-text="$t('configmeApp.computerCase.sizeMaxGpu')">Size Max Gpu</span>
          </dt>
          <dd>
            <span>{{ computerCase.sizeMaxGpu }}</span>
          </dd>
          <dt>
            <span v-text="$t('configmeApp.computerCase.sizeMaxVentirad')">Size Max Ventirad</span>
          </dt>
          <dd>
            <span>{{ computerCase.sizeMaxVentirad }}</span>
          </dd>
          <dt>
            <span v-text="$t('configmeApp.computerCase.sizeMaxPsu')">Size Max Psu</span>
          </dt>
          <dd>
            <span>{{ computerCase.sizeMaxPsu }}</span>
          </dd>
          <dt>
            <span v-text="$t('configmeApp.computerCase.hardDriveSlots')">Hard Drive Slots</span>
          </dt>
          <dd>
            <span>{{ computerCase.hardDriveSlots }}</span>
          </dd>
          <dt>
            <span v-text="$t('configmeApp.computerCase.frontPanelOutputs')">Front Panel Outputs</span>
          </dt>
          <dd>
            <span>{{ computerCase.frontPanelOutputs }}</span>
          </dd>
          <dt>
            <span v-text="$t('configmeApp.computerCase.fanIncluded')">Fan Included</span>
          </dt>
          <dd>
            <span>{{ computerCase.fanIncluded }}</span>
          </dd>
          <dt>
            <span v-text="$t('configmeApp.computerCase.fanSlotsAvailable')">Fan Slots Available</span>
          </dt>
          <dd>
            <span>{{ computerCase.fanSlotsAvailable }}</span>
          </dd>
          <dt>
            <span v-text="$t('configmeApp.computerCase.watercoolingCompatibility')">Watercooling Compatibility</span>
          </dt>
          <dd>
            <span>{{ computerCase.watercoolingCompatibility }}</span>
          </dd>
          <template v-if="computerCase.dimension">
            <dt>
              <span v-text="$t('configmeApp.dimension.height')">Height</span>
            </dt>
            <dd>
              <span>{{ computerCase.dimension.height }} mm</span>
            </dd>
            <dt>
              <span v-text="$t('configmeApp.dimension.width')">Width</span>
            </dt>
            <dd>
              <span>{{ computerCase.dimension.width }} mm</span>
            </dd>
            <dt>
              <span v-text="$t('configmeApp.dimension.length')">Length</span>
            </dt>
            <dd>
              <span>{{ computerCase.dimension.length }} mm</span>
            </dd>
          </template>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link
          v-if="computerCase.id"
          :to="{ name: 'ComputerCaseEdit', params: { computerCaseId: computerCase.id } }"
          custom
          v-slot="{ navigate }"
        >
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./computer-case-details.component.ts"></script>
