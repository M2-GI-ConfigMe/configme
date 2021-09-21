<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="configmeApp.ram.home.createOrEditLabel"
          data-cy="RamCreateUpdateHeading"
          v-text="$t('configmeApp.ram.home.createOrEditLabel')"
        >
          Create or edit a Ram
        </h2>
        <div>
          <div class="form-group" v-if="ram.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="ram.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('configmeApp.product.name')" for="computer-case-name">Nom</label>
            <input
              type="text"
              class="form-control"
              name="name"
              id="computer-case-name"
              data-cy="name"
              :class="{ valid: !$v.ram.name.$invalid, invalid: $v.ram.name.$invalid }"
              v-model="$v.ram.name.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('configmeApp.product.price')" for="computer-case-price">Prix</label>
            <input
              type="text"
              class="form-control"
              name="price"
              id="computer-case-price"
              data-cy="price"
              :class="{ valid: !$v.ram.price.$invalid, invalid: $v.ram.price.$invalid }"
              v-model="$v.ram.price.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('configmeApp.product.discount')" for="computer-case-discount">Réduction</label>
            <input
              type="text"
              class="form-control"
              name="discount"
              id="computer-case-discount"
              data-cy="discount"
              :class="{ valid: !$v.ram.discount.$invalid, invalid: $v.ram.discount.$invalid }"
              v-model="$v.ram.discount.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('configmeApp.product.stock')" for="computer-case-stock">Stock</label>
            <input
              type="text"
              class="form-control"
              name="stock"
              id="computer-case-stock"
              data-cy="stock"
              :class="{ valid: !$v.ram.stock.$invalid, invalid: $v.ram.stock.$invalid }"
              v-model="$v.ram.stock.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('configmeApp.product.img')" for="computer-case-img">Image</label>
            <input
              type="text"
              class="form-control"
              name="img"
              id="computer-case-img"
              data-cy="img"
              :class="{ valid: !$v.ram.img.$invalid, invalid: $v.ram.img.$invalid }"
              v-model="$v.ram.img.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('configmeApp.product.brand')" for="computer-case-brand">Marque</label>
            <input
              type="text"
              class="form-control"
              name="brand"
              id="computer-case-brand"
              data-cy="brand"
              :class="{ valid: !$v.ram.brand.$invalid, invalid: $v.ram.brand.$invalid }"
              v-model="$v.ram.brand.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('configmeApp.ram.speed')" for="ram-speed">Vitesse</label>
            <input
              type="number"
              class="form-control"
              name="speed"
              id="ram-speed"
              data-cy="speed"
              :class="{ valid: !$v.ram.speed.$invalid, invalid: $v.ram.speed.$invalid }"
              v-model="$v.ram.speed.$model"
              required
            />
            <div v-if="$v.ram.speed.$anyDirty && $v.ram.speed.$invalid">
              <small class="form-text text-danger" v-if="!$v.ram.speed.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
              <small class="form-text text-danger" v-if="!$v.ram.speed.numeric" v-text="$t('entity.validation.number')">
                This field should be a number.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('configmeApp.ram.type')" for="ram-type">Type</label>
            <select
              class="form-control"
              name="type"
              :class="{ valid: !$v.ram.type.$invalid, invalid: $v.ram.type.$invalid }"
              v-model="$v.ram.type.$model"
              id="ram-type"
              data-cy="type"
              required
            >
              <option value="DDR3" v-bind:label="$t('configmeApp.RamType.DDR3')">DDR3</option>
              <option value="DDR4" v-bind:label="$t('configmeApp.RamType.DDR4')">DDR4</option>
              <option value="DDR5" v-bind:label="$t('configmeApp.RamType.DDR5')">DDR5</option>
            </select>
            <div v-if="$v.ram.type.$anyDirty && $v.ram.type.$invalid">
              <small class="form-text text-danger" v-if="!$v.ram.type.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('configmeApp.ram.frequency')" for="ram-frequency">Frequency</label>
            <input
              type="number"
              class="form-control"
              name="frequency"
              id="ram-frequency"
              data-cy="frequency"
              :class="{ valid: !$v.ram.frequency.$invalid, invalid: $v.ram.frequency.$invalid }"
              v-model.number="$v.ram.frequency.$model"
              required
            />
            <div v-if="$v.ram.frequency.$anyDirty && $v.ram.frequency.$invalid">
              <small class="form-text text-danger" v-if="!$v.ram.frequency.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
              <small class="form-text text-danger" v-if="!$v.ram.frequency.numeric" v-text="$t('entity.validation.number')">
                This field should be a number.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('configmeApp.ram.unitSize')" for="ram-unitSize">Unit Size</label>
            <input
              type="number"
              class="form-control"
              name="unitSize"
              id="ram-unitSize"
              data-cy="unitSize"
              :class="{ valid: !$v.ram.unitSize.$invalid, invalid: $v.ram.unitSize.$invalid }"
              v-model.number="$v.ram.unitSize.$model"
              required
            />
            <div v-if="$v.ram.unitSize.$anyDirty && $v.ram.unitSize.$invalid">
              <small class="form-text text-danger" v-if="!$v.ram.unitSize.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
              <small class="form-text text-danger" v-if="!$v.ram.unitSize.numeric" v-text="$t('entity.validation.number')">
                This field should be a number.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('configmeApp.ram.quantity')" for="ram-quantity">Quantity</label>
            <input
              type="number"
              class="form-control"
              name="quantity"
              id="ram-quantity"
              data-cy="quantity"
              :class="{ valid: !$v.ram.quantity.$invalid, invalid: $v.ram.quantity.$invalid }"
              v-model.number="$v.ram.quantity.$model"
              required
            />
            <div v-if="$v.ram.quantity.$anyDirty && $v.ram.quantity.$invalid">
              <small class="form-text text-danger" v-if="!$v.ram.quantity.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
              <small class="form-text text-danger" v-if="!$v.ram.quantity.min" v-text="$t('entity.validation.min', { min: 2 })">
                This field should be at least 2.
              </small>
              <small class="form-text text-danger" v-if="!$v.ram.quantity.max" v-text="$t('entity.validation.max', { max: 8 })">
                This field cannot be longer than 8 characters.
              </small>
              <small class="form-text text-danger" v-if="!$v.ram.quantity.numeric" v-text="$t('entity.validation.number')">
                This field should be a number.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('configmeApp.ram.cas')" for="ram-cas">Cas</label>
            <input
              type="text"
              class="form-control"
              name="cas"
              id="ram-cas"
              data-cy="cas"
              :class="{ valid: !$v.ram.cas.$invalid, invalid: $v.ram.cas.$invalid }"
              v-model="$v.ram.cas.$model"
              required
            />
            <div v-if="$v.ram.cas.$anyDirty && $v.ram.cas.$invalid">
              <small class="form-text text-danger" v-if="!$v.ram.cas.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
            </div>
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
            :disabled="$v.ram.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./ram-update.component.ts"></script>
