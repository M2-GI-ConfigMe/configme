<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="configmeApp.ventirad.home.createOrEditLabel"
          data-cy="VentiradCreateUpdateHeading"
          v-text="$t('configmeApp.ventirad.home.createOrEditLabel')"
        >
          Create or edit a Ventirad
        </h2>
        <div>
          <div class="form-group" v-if="ventirad.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="ventirad.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('configmeApp.product.name')" for="ventirad-name">Nom</label>
            <input
              type="text"
              class="form-control"
              name="name"
              id="ventirad-name"
              data-cy="name"
              :class="{ valid: !$v.ventirad.name.$invalid, invalid: $v.ventirad.name.$invalid }"
              v-model="$v.ventirad.name.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('configmeApp.product.price')" for="ventirad-price">Prix</label>
            <input
              type="text"
              class="form-control"
              name="price"
              id="ventirad-price"
              data-cy="price"
              :class="{ valid: !$v.ventirad.price.$invalid, invalid: $v.ventirad.price.$invalid }"
              v-model="$v.ventirad.price.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('configmeApp.product.discount')" for="ventirad-discount">Réduction</label>
            <input
              type="text"
              class="form-control"
              name="discount"
              id="ventirad-discount"
              data-cy="discount"
              :class="{ valid: !$v.ventirad.discount.$invalid, invalid: $v.ventirad.discount.$invalid }"
              v-model="$v.ventirad.discount.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('configmeApp.product.stock')" for="ventirad-stock">Stock</label>
            <input
              type="text"
              class="form-control"
              name="stock"
              id="ventirad-stock"
              data-cy="stock"
              :class="{ valid: !$v.ventirad.stock.$invalid, invalid: $v.ventirad.stock.$invalid }"
              v-model="$v.ventirad.stock.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('configmeApp.product.img')" for="ventirad-img">Image</label>
            <v-file-input
              type="file"
              class="form-control"
              name="img"
              id="ventirad-img"
              ref="img"
              accept="image/*"
              :class="{ valid: !$v.ventirad.img.$invalid, invalid: $v.ventirad.img.$invalid }"
              @change="selectFile"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('configmeApp.product.brand')" for="ventirad-brand">Marque</label>
            <input
              type="text"
              class="form-control"
              name="brand"
              id="ventirad-brand"
              data-cy="brand"
              :class="{ valid: !$v.ventirad.brand.$invalid, invalid: $v.ventirad.brand.$invalid }"
              v-model="$v.ventirad.brand.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('configmeApp.ventirad.rangeFanSpeed')" for="ventirad-rangeFanSpeed"
              >Range Fan Speed</label
            >
            <input
              type="text"
              class="form-control"
              name="rangeFanSpeed"
              id="ventirad-rangeFanSpeed"
              data-cy="rangeFanSpeed"
              :class="{ valid: !$v.ventirad.rangeFanSpeed.$invalid, invalid: $v.ventirad.rangeFanSpeed.$invalid }"
              v-model="$v.ventirad.rangeFanSpeed.$model"
              required
            />
            <div v-if="$v.ventirad.rangeFanSpeed.$anyDirty && $v.ventirad.rangeFanSpeed.$invalid">
              <small class="form-text text-danger" v-if="!$v.ventirad.rangeFanSpeed.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label v-text="$t('configmeApp.computerCase.sockets')">Sockets</label>
            <select class="form-control" multiple name="sockets" v-model="sockets">
              <option v-for="(socket, key) of socketTypes" :value="socket" :key="key">{{ socket }}</option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('configmeApp.ventirad.noise')" for="ventirad-noise">Noise</label>
            <input
              type="number"
              class="form-control"
              name="noise"
              id="ventirad-noise"
              data-cy="noise"
              :class="{ valid: !$v.ventirad.noise.$invalid, invalid: $v.ventirad.noise.$invalid }"
              v-model.number="$v.ventirad.noise.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('configmeApp.ventirad.hasThermalPaste')" for="ventirad-hasThermalPaste"
              >Has Thermal Paste</label
            >
            <input
              type="checkbox"
              class="form-check"
              name="hasThermalPaste"
              id="ventirad-hasThermalPaste"
              data-cy="hasThermalPaste"
              :class="{ valid: !$v.ventirad.hasThermalPaste.$invalid, invalid: $v.ventirad.hasThermalPaste.$invalid }"
              v-model="$v.ventirad.hasThermalPaste.$model"
              required
            />
            <div v-if="$v.ventirad.hasThermalPaste.$anyDirty && $v.ventirad.hasThermalPaste.$invalid">
              <small class="form-text text-danger" v-if="!$v.ventirad.hasThermalPaste.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('configmeApp.dimension.height')" for="ventirad-height"
              >Watercooling Compatibility</label
            >
            <input
              type="text"
              class="form-control"
              name="height"
              id="ventirad-height"
              data-cy="height"
              :class="{
                valid: !$v.ventirad.dimension.height.$invalid,
                invalid: $v.ventirad.dimension.height.$invalid,
              }"
              v-model="$v.ventirad.dimension.height.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('configmeApp.dimension.height')" for="ventirad-width"
              >Watercooling Compatibility</label
            >
            <input
              type="text"
              class="form-control"
              name="width"
              id="ventirad-width"
              data-cy="width"
              :class="{
                valid: !$v.ventirad.dimension.width.$invalid,
                invalid: $v.ventirad.dimension.width.$invalid,
              }"
              v-model="$v.ventirad.dimension.width.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('configmeApp.dimension.length')" for="ventirad-length"
              >Watercooling Compatibility</label
            >
            <input
              type="text"
              class="form-control"
              name="height"
              id="ventirad-length"
              data-cy="length"
              :class="{
                valid: !$v.ventirad.dimension.length.$invalid,
                invalid: $v.ventirad.dimension.length.$invalid,
              }"
              v-model="$v.ventirad.dimension.length.$model"
            />
          </div>
        </div>
        <div>
          <button type="button" id="cancel-save" data-cy="entityCreateCancelButton" class="btn btn-secondary" v-on:click="previousState()">
            <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
          </button>
          <button
            type="submit"
            id="save-entity"
            data-cy="entityCreateSaveButton"
            :disabled="$v.ventirad.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./ventirad-update.component.ts"></script>
